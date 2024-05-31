package com.newcord.articleservice.domain.article_version.service;

import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.OperationEntityType;
import com.newcord.articleservice.domain.article_version.entity.OperationType;
import com.newcord.articleservice.domain.article_version.entity.Version;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.repository.ArticleVersionRepository;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleVersionCommandServiceImpl implements ArticleVersionCommandService{
    private final ArticleVersionRepository articleVersionRepository;
    private final Object lock = new Object();


    @Override
    @Transactional
    public VersionOperation applyOperation(VersionOperation clientOperation, String version,
        Long articleId) {
        VersionOperation appliedOperation = clientOperation;
        synchronized (lock) {
            //버전상 충돌하는 경우 처리
            ArticleVersion articleVersion = articleVersionRepository.findById(articleId)
                .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

            String[] versionArr = version.split("\\.");

            //요청온 버전부터 operation을 읽어오기 위한 버전 리스트
            List<Version> versions = articleVersion.getVersions().subList(Integer.parseInt(versionArr[0]),
                articleVersion.getVersions().size());

            if(!appliedOperation.getOperationType().equals(OperationType.SEQUENCE_UPDATE)){
                // 버전의 operation을 돌며 position 증감
                for (Version v : versions) {
                    int cnt = 0;
                    for (VersionOperation serverOperation : v.getOperations()) {
                        if(cnt <= Integer.parseInt(versionArr[1]))
                            continue;
                        if(serverOperation.getEntityType() != appliedOperation.getEntityType()) {
                            continue;
                        }
                        // 텍스트 수정(블럭 내부)인 경우, 같은 블럭끼리만 OT수행해야함
                        if(serverOperation.getEntityType().equals(OperationEntityType.TEXT)){
                            if(!serverOperation.getId().equals(appliedOperation.getId())){
                                continue;
                            }
                        }

                        if(appliedOperation.getOperationType().equals(OperationType.INSERT)){
                            if(serverOperation.getOperationType().equals(OperationType.INSERT)) {
                                if(appliedOperation.getPosition() > serverOperation.getPosition()) {
                                    appliedOperation.addPosition(serverOperation.getContent().length());
                                }
                            }
                            else if (appliedOperation.getOperationType().equals(OperationType.DELETE)) {
                                if(appliedOperation.getPosition() > serverOperation.getPosition()){
                                    appliedOperation.addPosition(-1);
                                }
                            }
                        }
                        else if(appliedOperation.getOperationType().equals(OperationType.DELETE)){
                            if(serverOperation.getOperationType().equals(OperationType.INSERT)) {
                                if(appliedOperation.getPosition() >= serverOperation.getPosition()) {
                                    appliedOperation.addPosition(serverOperation.getContent().length());
                                }
                            }
                            else if (appliedOperation.getOperationType().equals(OperationType.DELETE)) {
                                if(appliedOperation.getPosition() <= serverOperation.getPosition()){
                                    appliedOperation.addPosition(-1);
                                }
                            }
                        }
                        cnt++;
                    }
                }
            }

            // operation의 position 수정하고 DB에 저장
            articleVersion.getVersions().get(articleVersion.getVersions().size() - 1).getOperations().add(appliedOperation);
            articleVersionRepository.save(articleVersion);
            return appliedOperation;
        }
    }

    @Override
    public ArticleVersion createArticleVersion(Long articleId) {
        ArticleVersion articleVersion = ArticleVersion.builder()
            .id(articleId)
            .versions(new ArrayList<>())
            .build();

        Version version = Version.builder()
            .timestamp(LocalDateTime.now())
            .operations(new ArrayList<>())
            .build();

        articleVersion.getVersions().add(version);

        articleVersionRepository.save(articleVersion);

        return articleVersion;
    }

    @Override
    @Transactional
    public Version updateVersion(Long articleID){
        System.out.println("articleID = " + articleID);
        ArticleVersion articleVersion = articleVersionRepository.findById(articleID)
                .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));
        Version version = Version.builder()
                .timestamp(LocalDateTime.now())
                .operations(new ArrayList<>())
                .build();

        articleVersion.getVersions().add(version);

        articleVersionRepository.save(articleVersion);
        return version;
    }

    @Override
    public String getLatestVersion(Long articleID) {
        ArticleVersion articleVersion = articleVersionRepository.findById(articleID)
            .orElseThrow(() -> new ApiException(ErrorStatus._ARTICLE_NOT_FOUND));

        List<Version> versions = articleVersion.getVersions();
        int lastVersionIdx = versions.size() - 1;
        List<VersionOperation> operations = versions.get(lastVersionIdx).getOperations();
        int lastOperation = 0;
        if(!operations.isEmpty())
            lastOperation = operations.size() - 1;

        return lastVersionIdx + "." + lastOperation;
    }
}

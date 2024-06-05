package com.newcord.articleservice.domain.log.Service;


import com.newcord.articleservice.domain.article_version.entity.ArticleVersion;
import com.newcord.articleservice.domain.article_version.entity.Version;
import com.newcord.articleservice.domain.article_version.entity.VersionOperation;
import com.newcord.articleservice.domain.article_version.service.ArticleVersionQueryService;
import com.newcord.articleservice.domain.articles.entity.Article;
import com.newcord.articleservice.domain.articles.repository.ArticlesRepository;
import com.newcord.articleservice.domain.block.entity.Block;
import com.newcord.articleservice.domain.block.repository.BlockRepository;
import com.newcord.articleservice.domain.editor.dto.EditorResponse;
import com.newcord.articleservice.domain.editor.entity.Editor;
import com.newcord.articleservice.domain.editor.repository.EditorRepository;
import com.newcord.articleservice.domain.editor.service.EditorQueryService;
import com.newcord.articleservice.domain.log.dto.LogResponse.*;
import com.newcord.articleservice.global.common.exception.ApiException;
import com.newcord.articleservice.global.common.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogComposeServiceImpl implements LogComposeService{
    private final ArticleVersionQueryService articleVersionQueryService;
    private final EditorQueryService editorQueryService;
    private final BlockRepository blockRepository;
    private final ArticlesRepository articlesRepository;
    private final EditorRepository editorRepository;
    @Override
    public EditorLogListResponseDTO getEditorsLogData(Long postID) {
        EditorResponse.EditorListResponseDTO editorListResponseDTO = editorQueryService.getAllEditorsByPostId(postID);

        List<Long> userIds = editorListResponseDTO.getUserID();

        Set<EditorLogResponseDTO> uniqueResults = new HashSet<>();
        ArticleVersion articleVersion = articleVersionQueryService.findArticleVersionById(postID);

        for (Version version : articleVersion.getVersions()) {
            List<VersionOperation> operations = version.getOperations();
            for (VersionOperation operation : operations) {
                for (Long userId : userIds) {
                    if (operation.getUpdated_by().getUpdater_id().equals(userId)) {
                        EditorLogResponseDTO editorLogResponseDTO = EditorLogResponseDTO.builder()
                                .userID(userId)
                                .blockId(operation.getId().toString())
                                .build();

                        uniqueResults.add(editorLogResponseDTO);
                    }
                }
            }
        }
        List<EditorLogResponseDTO> editorLogResponseDTOList = new ArrayList<>(uniqueResults);
        return EditorLogListResponseDTO.builder()
                .editorLogResponseDTOS(editorLogResponseDTOList)
                .build();
    }

    @Override
    public BlockLogListDataResponseDTO getBlockCreator(Long creator_id) {
        List<Block> blocks=blockRepository.findByCreatedByCreatorId(creator_id);
        List<BlockLogDataResponseDTO> result = new ArrayList<>();
        for(Block block:blocks){
            BlockLogDataResponseDTO blockLogDataResponseDTO= BlockLogDataResponseDTO.builder()
                    .creatorId(block.getCreated_by().getCreator_id())
                    .blockId(block.getId().toString())
                    .build();
            result.add(blockLogDataResponseDTO);
        }
     List<BlockLogDataResponseDTO> blockLogDataResponseDTOList=new ArrayList<>(result);
        return BlockLogListDataResponseDTO.builder()
                .blockLogDataResponseDTOList(blockLogDataResponseDTOList)
                .build();
    }

    @Override
    public List<BlockCreatorDataResponseDTO> getBlockCreatorData(Long post_id) {
        System.out.println("post_id = " + post_id);
        List<Editor> editors = editorRepository.findByPostId(post_id);
        Optional<Article> articleOpt = articlesRepository.findById(post_id);

        if (!articleOpt.isPresent()) {
            throw new ApiException(ErrorStatus._ARTICLE_NOT_FOUND);  // 예외 처리
        }
        Article article = articleOpt.get();
        List<String> blockListIds = article.getBlock_list();
        List<ObjectId> blockObjectIds = blockListIds.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());
        List<Block> blocks = blockRepository.findAllById(blockObjectIds);
        List<BlockCreatorDataResponseDTO> result = new ArrayList<>();
        
        for(Editor editor: editors){
            List<String> blockresult=new ArrayList<>();
            for(Block block:blocks){
                if (block.getCreated_by().getCreator_id().equals(editor.getUserID())) {
                    blockresult.add(block.getId().toString());
                }
            }
            BlockCreatorDataResponseDTO blockCreatorDataResponseDTO=BlockCreatorDataResponseDTO.builder()
                    .creatorId(editor.getUserID())
                    .blockId(blockresult)
                    .build();

            result.add(blockCreatorDataResponseDTO);
        }

        return result;
    }

}

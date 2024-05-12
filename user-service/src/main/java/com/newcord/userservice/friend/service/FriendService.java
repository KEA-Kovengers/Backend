package com.newcord.userservice.friend.service;

import com.newcord.userservice.friend.domain.Friend;
import com.newcord.userservice.friend.dto.FriendListdto;
import com.newcord.userservice.friend.repository.FriendRepository;
import com.newcord.userservice.friend.status.FriendshipStatus;
import com.newcord.userservice.user.domain.Users;
import com.newcord.userservice.user.repository.UsersRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FriendService {

    private final UsersRepository usersRepository;
    private final FriendRepository friendRepository;


    public FriendService(UsersRepository usersRepository, FriendRepository friendRepository) {
        this.friendRepository=friendRepository;
        this.usersRepository = usersRepository;
    }


    @Transactional
    public String createFriendship(Long fromID, Long toID) throws Exception {
        // 유저 정보를 모두 가져옴
        Users fromUser = usersRepository.findById(fromID).orElseThrow(() -> new Exception("회원 조회 실패"));
        Users toUser = usersRepository.findById(toID).orElseThrow(() -> new Exception("회원 조회 실패"));
        Optional<Friend> existingFriendship = friendRepository.findAllByFriendIDAndUserIDAndStatus(fromID, toID, FriendshipStatus.WAITING);
        Optional<Friend> alreadyFriendship=friendRepository.findAllByFriendIDAndUserIDAndStatus(fromID,toID,FriendshipStatus.ACCEPT);

        if (existingFriendship.isPresent()) {
            log.info(String.valueOf(existingFriendship));
            throw new Exception("이미 친구 요청을 보냈습니다.");
        }

        if(alreadyFriendship.isPresent()){
            throw new Exception("이미 친구입니다.");
        }

        // 요청을 보내는 사람 정보 저장
        Friend friendshipFrom = Friend.builder()
                .users(fromUser)
                .friendID(toID)
                .status(FriendshipStatus.WAITING)
                .isFrom(true)
                .build();

        // 요청을 받는 사람 정보 저장
        Friend friendshipTo = Friend.builder()
                .users(toUser)
                .userID(toID)
                .friendID(fromID)
                .status(FriendshipStatus.WAITING)
                .isFrom(false)
                .build();

        // 저장
        friendRepository.save(friendshipTo);
        friendRepository.save(friendshipFrom);

        // 매칭되는 친구요청의 아이디를 서로 저장
        friendshipTo.setCounterpartId(friendshipFrom.getId());
        friendshipFrom.setCounterpartId(friendshipTo.getId());

        return "친구 요청 성공";
    }



    @Transactional
    public String approveFriendshipRequest(Long friendshipId) throws Exception {
        // 누를 친구 요청과 매칭되는 상대방 친구 요청 둘다 가져옴
        Friend friendship = friendRepository.findById(friendshipId).orElseThrow(() -> new Exception("친구 요청 조회 실패"));
        Long fid=friendship.getCounterpartId();
        log.info("친구"+fid);
        log.info("상대방"+String.valueOf(friendship.getCounterpartId()));
        Friend counterFriendship = friendRepository.findById(friendship.getCounterpartId()).orElseThrow(() -> new Exception("친구 요청 조회 실패"));

        // 둘다 상태를 ACCEPT로 변경함
        friendship.acceptFriendshipRequest();
        counterFriendship.acceptFriendshipRequest();

        return "승인 성공";
    }

    @Transactional
    public String rejectFriendshipRequest(Long friendshipId) throws Exception{
        Friend friendship=friendRepository.findById(friendshipId).orElseThrow(()->new Exception("친구 요청 조회 실패"));
        Long fid=friendship.getCounterpartId();
        Friend counterFriendship=friendRepository.findById(friendship.getCounterpartId()).orElseThrow(()->new Exception("친구 요청 조회 실패"));

        friendRepository.deleteById(friendship.getId());
        friendRepository.deleteById(counterFriendship.getId());

        return "거절 성공";

    }

    @Transactional
    public String deleteFriendship(Long friendshipId) throws Exception{
        Friend friendship=friendRepository.findById(friendshipId).orElseThrow(()->new Exception("친구 목록 조회 실패"));
        Long fid=friendship.getCounterpartId();
        Friend counterFriendship=friendRepository.findById(friendship.getCounterpartId()).orElseThrow(()->new Exception("친구 목록 조회 실패"));

        friendRepository.deleteById(friendship.getId());
        friendRepository.deleteById(counterFriendship.getId());

        return "삭제 성공";

    }

    @Transactional
    public ResponseEntity<?> getWaitingFriendList(Long ID) throws Exception {

        Users users = usersRepository.findById(ID).orElseThrow(() -> new Exception("회원 조회 실패"));

        List<Friend> friendshipList = users.getFriendList();

        // 조회된 결과 객체를 담을 Dto 리스트
        List<FriendListdto> result = new ArrayList<>();

        for (Friend x : friendshipList) {
            // 보낸 요청이 아니고 && 수락 대기중인 요청만 조회
            if (!x.isFrom() && x.getStatus() == FriendshipStatus.WAITING) {
                Users friend = usersRepository.findById(x.getFriendID()).orElseThrow(() -> new Exception("회원 조회 실패"));
                FriendListdto dto = FriendListdto.builder()
                        .friendshipId(x.getId())
                        .friendId(friend.getId())
                        .friendName(friend.getNickName())
                        .status(x.getStatus())
                        .imgUrl(friend.getProfileImg())
                        .build();
                result.add(dto);
            }
        }
        // 결과 반환
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> getAcceptFriendList(Long id)throws Exception{
        Users users = usersRepository.findById(id).orElseThrow(() -> new Exception("회원 조회 실패"));

        List<Friend> friendshipList = users.getFriendList();

        // 조회된 결과 객체를 담을 Dto 리스트
        List<FriendListdto> result = new ArrayList<>();

        for (Friend x : friendshipList) {
            // 보낸 요청이 아니고 && 수락 대기중인 요청만 조회
            if (x.getStatus() == FriendshipStatus.ACCEPT) {
                Users friend = usersRepository.findById(x.getFriendID()).orElseThrow(() -> new Exception("회원 조회 실패"));
                FriendListdto dto = FriendListdto.builder()
                        .friendshipId(x.getId())
                        .friendId(friend.getId())
                        .friendName(friend.getNickName())
                        .status(x.getStatus())
                        .imgUrl(friend.getProfileImg())
                        .build();
                result.add(dto);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

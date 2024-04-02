//package com.newcord.userservice.test;
//
//import com.newcord.userservice.folder.domain.Folder;
//import com.newcord.userservice.folder.domain.FolderPost;
//import com.newcord.userservice.folder.domain.FolderPostId;
//import com.newcord.userservice.folder.repository.FolderPostRepository;
//import com.newcord.userservice.folder.repository.FolderRepository;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//public class InsertDb {
//    private final FolderRepository folderRepository;
//    private final FolderPostRepository folderPostRepository;
//
//    @Autowired
//    public InsertDb(FolderRepository folderRepository, FolderPostRepository folderPostRepository) {
//        this.folderRepository = folderRepository;
//        this.folderPostRepository = folderPostRepository;
//    }
//
//    private Folder makeFolder(Long id, Long user_id, String folderName){
//        return Folder.builder().id(id).user_id(user_id).folderName(folderName).build();
//    }
//    private FolderPost makeFolderPost(FolderPostId folderPostId){
//        return FolderPost.builder().folderPostId(folderPostId).build();
//    }
//
//    @Test
//    public void InsertFolderAndFolderPost() {
//        folderRepository.save(makeFolder(1L, 1L, "somi"));
//        folderRepository.save(makeFolder(2L, 2L, "nam"));
//
//        Folder folder = folderRepository.findById(2L).orElseThrow();
//        FolderPost folderPost = makeFolderPost(new FolderPostId(folder.getId(), 1L));
//        folderPostRepository.save(folderPost);
//    }
//}

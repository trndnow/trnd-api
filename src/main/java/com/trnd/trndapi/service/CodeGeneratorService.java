package com.trnd.trndapi.service;

import com.trnd.trndapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CodeGeneratorService {
    private final UserRepository userRepository;
    private final Set<String> codeCache = ConcurrentHashMap.newKeySet();
    private static final Random random = new Random();

    public CodeGeneratorService(UserRepository userRepository) {
        this.userRepository = userRepository;
        loadExistingCodesIntoCache();
    }
    private void loadExistingCodesIntoCache() {
        userRepository.findAllUserCode().forEach(codeCache::add);
    }

    public synchronized String generateUniqueCode(String userType){
        String uniqueCode;
        do{
            uniqueCode = createCode(userType.charAt(5));
        }while(codeCache.contains(uniqueCode));

        codeCache.add(uniqueCode);
        return uniqueCode;
    }

    private String createCode(char typeInitial) {
        return String.valueOf(typeInitial+""+ (10000 + random.nextInt(900000)));
    }
}

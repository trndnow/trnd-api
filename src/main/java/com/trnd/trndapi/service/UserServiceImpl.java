package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.entity.CampaignAffiliate;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.mapper.UserMapper;
import com.trnd.trndapi.repository.CampaignAffiliateRepository;
import com.trnd.trndapi.repository.UserRepository;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MerchantService merchantService;
    private final CampaignService campaignService;
    private final CampaignAffiliateRepository campaignAffiliateRepository;
    private final UserMapper userMapper;

    /**
     * @return
     */
    @Override
    public ResponseEntity<?> getPendingApproval(ERole role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "registrationDateTime");
        String loggedInUserEmail = SecurityUtils.getLoggedInUserName();
        if(role.equals(ERole.ROLE_MERCHANT) && SecurityUtils.hasRole(ERole.ROLE_ADMIN.name())){
            List<User> userList = userRepository.findByUserStatusAndRole_NameOrderByRegistrationDateTimeAsc(AccountStatus.INACTIVE, ERole.ROLE_MERCHANT, sort);
            if(userList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.builder()
                        .code(HttpStatus.NO_CONTENT.value())
                        .statusCode(HttpStatus.NO_CONTENT.toString())
                        .statusMsg("NO RECORD FOUND")
                        .build());
            }
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .code(HttpStatus.OK.value())
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(userMapper.toDtoList(userList))
                    .build());

        } else if (role.equals(ERole.ROLE_AFFILIATE)) {
            MerchantDto merchantDto =  merchantService.findMerchantByEmail(loggedInUserEmail);
            List<CampaignAffiliate> byMerchantMerchIdAndAffiliateUserUserStatus = campaignAffiliateRepository.findByMerchant_MerchIdAndAffiliate_User_UserStatus(merchantDto.getMerchId(), AccountStatus.INACTIVE);
            List<UUID> affUniqueCode =  byMerchantMerchIdAndAffiliateUserUserStatus.stream().map(record -> UUID.fromString(record.getAffiliate().getAffUniqueCode())).toList();
            List<User> byIdInAndUserStatusOrderByRegistrationDateTimeAsc = userRepository.findByUniqueIdInAndUserStatusOrderByRegistrationDateTimeAsc(affUniqueCode,AccountStatus.INACTIVE);
//      List<User> byUserStatus = userRepository.findByUserStatusAndRole_NameOrderByRegistrationDateTimeAsc(AccountStatus.INACTIVE, role, sort);
            if(byIdInAndUserStatusOrderByRegistrationDateTimeAsc.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.builder()
                        .code(HttpStatus.NO_CONTENT.value())
                        .statusCode(HttpStatus.NO_CONTENT.toString())
                        .statusMsg("NO RECORD FOUND")
                        .build());
            }
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .code(HttpStatus.OK.value())
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg("RECORD FOUND")
                    .data(userMapper.toDtoList(byIdInAndUserStatusOrderByRegistrationDateTimeAsc))
                    .build());

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .statusCode(HttpStatus.BAD_REQUEST.toString())
                .statusMsg("Unable to get the records")
                .build());


    }

    /**
     * @param userDtoList
     * @param role
     * @return
     */
    @Override
    @Transactional
    public ResponseDto approveUser(List<UserDto> userDtoList, ERole role) {
        try{
            List<User> usersToUpdate = new ArrayList<>();
            List<User> usersFiledToUpdate = new ArrayList<>();
            if(role.equals(ERole.ROLE_MERCHANT)){
                userDtoList.forEach(userDto -> {
                    User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UsernameNotFoundException("ERROR: User not found with user id: "+ userDto.getId()));
                    if(user != null && user.getRole().getName().equals(ERole.ROLE_MERCHANT)){
                        if(user.isEmailVerifiedFlag()){
                            user.setUserStatus(AccountStatus.ACTIVE);
                            usersToUpdate.add(user);
                        }else{
                            user.setUserStatus(AccountStatus.INACTIVE);
                            usersFiledToUpdate.add(user);
                        }
                    }
                });
            } else if (role.equals(ERole.ROLE_AFFILIATE)) {
                userDtoList.forEach(userDto -> {
                    User user = userRepository.findById(userDto.getId()).orElse(null);
                    if(user != null && user.getRole().getName().equals(ERole.ROLE_AFFILIATE)){
                        user.setUserStatus(AccountStatus.ACTIVE);
                        usersToUpdate.add(user);
                    }
                });
            }

            /** Associate default campaign to the merchant*/
            List<User> userList = userRepository.saveAll(usersToUpdate);
            if(role.equals(ERole.ROLE_MERCHANT)){
                userList.forEach(user -> {
                    MerchantDto merchantByMerchantUniqueCode = merchantService.getMerchantByMerchantCode(user.getUserCode());
                    merchantByMerchantUniqueCode.setMerchStatus(AccountStatus.ACTIVE);
                    merchantByMerchantUniqueCode.setMerchActivationDtm(LocalDateTime.now());
                    ResponseDto responseDto = merchantService.activateMerchant(merchantByMerchantUniqueCode);
                    CampaignDto campaignDto = campaignService.defaultCampaignAssociation(merchantByMerchantUniqueCode);
                    /** TODO:Create a mechanism to log failed operation, so the we can run a batch job to retry it at later point of time.*/
                });
            }
            return ResponseDto.builder()
                    .code(HttpStatus.OK.value())
                    .statusCode(HttpStatus.OK.toString())
                    .statusMsg( role.equals(ERole.ROLE_MERCHANT) ? "MERCHANT ACTIVATED SUCCESSFULLY" :"AFFILIATE ACTIVATED SUCCESSFULLY" )
                    .data(userMapper.toDtoList(userList))
                    .build();
        }catch (Exception e){
            log.error("ACCOUNT ACTIVATION FAILED {}",e.getLocalizedMessage());
            return ResponseDto.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .statusCode(HttpStatus.BAD_REQUEST.toString())
                    .statusMsg("ACCOUNT ACTIVATION FAILED")
                    .build();
        }

    }
}

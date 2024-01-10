package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import com.trnd.trndapi.mapper.UserMapper;
import com.trnd.trndapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final MerchantService merchantService;
    private final CampaignService campaignService;
    private final UserMapper userMapper;

    /**
     * @return
     */
    @Override
    public List<UserDto> getPendingApproval(ERole role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "registrationDateTime");
        List<User> byUserStatus = userRepository.findByUserStatusAndRole_NameOrderByRegistrationDateTimeAsc(AccountStatus.INACTIVE, role, sort);
        return userMapper.toDtoList(byUserStatus);
    }

    /**
     * @param userDtoList
     * @param role
     * @return
     */
    @Override
    @Transactional
    public List<UserDto> approveUser(List<UserDto> userDtoList, ERole role) {
        List<User> usersToUpdate = new ArrayList<>();
        List<User> usersFiledToUpdate = new ArrayList<>();
        if(role.equals(ERole.ROLE_MERCHANT)){
            userDtoList.forEach(userDto -> {
                User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new MerchantNoFoundException("ERROR: Merchant not found with user id: "+ userDto.getId()));
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
                merchantService.updateMerchant(merchantByMerchantUniqueCode);
                CampaignDto campaignDto = campaignService.defaultCampaignAssociation(merchantByMerchantUniqueCode);
                /** TODO:Create a mechanism to log failed operation, so the we can run a batch job to retry it at later point of time.*/
            });
        }
        return userMapper.toDtoList(userList);
    }
}

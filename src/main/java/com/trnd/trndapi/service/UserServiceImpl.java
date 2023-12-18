package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignDto;
import com.trnd.trndapi.dto.MerchantDto;
import com.trnd.trndapi.dto.UserDto;
import com.trnd.trndapi.entity.User;
import com.trnd.trndapi.enums.AccountStatus;
import com.trnd.trndapi.enums.ERole;
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

    /**
     * @return
     */
    @Override
    public List<UserDto> getPendingApproval(ERole role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "registrationDateTime");
        List<User> byUserStatus = userRepository.findByUserStatusAndRole_NameOrderByRegistrationDateTimeAsc(AccountStatus.INACTIVE, role, sort);
        return UserMapper.INSTANCE.toDtoList(byUserStatus);
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
        if(role.equals(ERole.ROLE_MERCHANT)){
            userDtoList.forEach(userDto -> {
                User user = userRepository.findById(userDto.getId()).orElse(null);
                if(user != null && user.getRole().getName().equals(ERole.ROLE_MERCHANT)){
                    user.setUserStatus(AccountStatus.ACTIVE);
                    usersToUpdate.add(user);
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
                MerchantDto merchantByMerchantUniqueCode = merchantService.getMerchantByMerchantUniqueCode(user.getUserCode());
                CampaignDto campaignDto = campaignService.defaultCampaignAssociation(merchantByMerchantUniqueCode);
                /** TODO:Create a mechanism to log failed operation, so the we can run a batch job to retry it at later point of time.*/
            });
        }
        return UserMapper.INSTANCE.toDtoList(userList);
    }
}

package com.trnd.trndapi.service;

import com.trnd.trndapi.dto.CampaignCategoryRefDto;
import org.springframework.stereotype.Service;

@Service
public interface CampaignCategoryRefService {

    CampaignCategoryRefDto getCampaignCategoryByName(String name);

}

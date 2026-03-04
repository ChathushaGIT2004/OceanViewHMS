package org.example.Services;


import org.example.DTO.HelpItemDTO;

import java.util.List;

public interface IHelpService {
    List<HelpItemDTO> getAllHelpItems();
    List<HelpItemDTO> getHelpByCategory(String category);
    HelpItemDTO getHelpById(int id);
}
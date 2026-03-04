package org.example.Services.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.DTO.HelpItemDTO;
import org.example.Services.IHelpService;


import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class HelpService implements IHelpService {

    private List<HelpItemDTO> helpItems;

    public HelpService() {
        loadHelpItems();
    }

    private void loadHelpItems() {
        try (FileReader reader = new FileReader("src/main/resources/help.json")) {
            Type listType = new TypeToken<List<HelpItemDTO>>() {}.getType();
            helpItems = new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            helpItems = List.of();
        }
    }

    @Override
    public List<HelpItemDTO> getAllHelpItems() {
        return helpItems;
    }

    @Override
    public List<HelpItemDTO> getHelpByCategory(String category) {
        return helpItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public HelpItemDTO getHelpById(int id) {
        return helpItems.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
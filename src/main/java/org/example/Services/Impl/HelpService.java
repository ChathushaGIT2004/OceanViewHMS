package org.example.Services.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.DTO.HelpItemDTO;
import org.example.Services.IHelpService;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class HelpService implements IHelpService {

    private List<HelpItemDTO> helpItems;

    public HelpService() {
        loadHelpItems();
    }

    private void loadHelpItems() {
        try (InputStreamReader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("Help/Help.json"))) {

            if (reader == null) {
                throw new RuntimeException("Help.json not found in resources folder");
            }

            Type listType = new TypeToken<List<HelpItemDTO>>() {}.getType();
            helpItems = new Gson().fromJson(reader, listType);

            System.out.println("Help items loaded: " + helpItems.size());

        } catch (Exception e) {
            e.printStackTrace();
            helpItems = List.of(); // fallback to empty list
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
package org.example.DTO;


public class HelpItemDTO {
    private int id;
    private String category;
    private String title;
    private String description;
    private String more;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMore() { return more; }
    public void setMore(String more) { this.more = more; }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.karl.dvdlibrary.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author karl
 */
public class DVD {
    
    // Unique identifier.
    private String idNumber;
    private String title;
    // Could use a Date object here but we don't need to order by date.
    // This is easier.
    private LocalDate releaseDate;
    private String mpaaRating;
    private String directorName;
    private String studio;
    private String userNote;
    
    public DVD (String idNumber) {
        this.idNumber = idNumber;
    }
    
    // Getters
    public String getIdNumber() {
        return idNumber;
    }
    
    public String getTitle() {
        return title;
    }
    
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    public String getMpaaRating() {
        return mpaaRating;
    }
    
    public String getDirectorName() {
        return directorName;
    }
    
    public String getStudio() {
        return studio;
    }
    
    public String getUSerNote() {
        return userNote;
    }
    
    // Setters
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }
    
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
    
    public void setStudio(String studio) {
        this.studio = studio;
    }
    
    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.idNumber);
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + Objects.hashCode(this.releaseDate);
        hash = 17 * hash + Objects.hashCode(this.mpaaRating);
        hash = 17 * hash + Objects.hashCode(this.directorName);
        hash = 17 * hash + Objects.hashCode(this.studio);
        hash = 17 * hash + Objects.hashCode(this.userNote);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DVD other = (DVD) obj;
        if (!Objects.equals(this.idNumber, other.idNumber)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.mpaaRating, other.mpaaRating)) {
            return false;
        }
        if (!Objects.equals(this.directorName, other.directorName)) {
            return false;
        }
        if (!Objects.equals(this.studio, other.studio)) {
            return false;
        }
        if (!Objects.equals(this.userNote, other.userNote)) {
            return false;
        }
        return Objects.equals(this.releaseDate, other.releaseDate);
    }
    
    
}

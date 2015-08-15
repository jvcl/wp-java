package com.flyonet.wpjava;

/**
 * This file is part of WP-JAVA.
 *
 *  WP-JAVA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WP-JAVA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WP-JAVA.  If not, see <http://www.gnu.org/licenses/>.
 */

public class Page {
    private Integer ID;
    private String title;
    private String status;
    private String type;
    private Author author;
    private String content;
    private String parent;
    private String link;
    private String date;
    private String modified;
    private String content_raw;

    public enum STATUS {
        DRAFT, PUBLISH, PENDING, FUTURE, PRIVATE
    }
    public enum TYPE {
        POST, PAGE, PENDING, FUTURE, PRIVATE
    }

    private Page(){}

    public Page(String title, String content_raw, TYPE type, STATUS status) {
        this.title = title;
        this.content_raw = content_raw;
        this.status = status.name().toLowerCase();
        this.type = type.name().toLowerCase();
    }

    @Override
    public String toString() {
        return ID + " " + title + " " + status;
    }
    public Author getAuthor(){
        return author;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status.name().toLowerCase();
    }

    public String getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type.name().toLowerCase();
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModified() {
        return modified;
    }

    public void setContent_raw(String content_raw) {
        this.content_raw = content_raw;
    }

    private static class Author {
        private int ID;
        private String username;
        private String name;
        private String first_name;
        private String last_name;

        @Override
        public String toString() {
            return ID + " " + username;
        }
    }
}

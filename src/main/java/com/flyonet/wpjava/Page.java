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
    private int ID;
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

    @Override
    public String toString() {
        return ID + " " + title + " " + status + " " + content;
    }
    public Author getAuthor(){
        return author;
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

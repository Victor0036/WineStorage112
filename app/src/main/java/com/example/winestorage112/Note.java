package com.example.winestorage112;

    public class Note {
        private long id;
        private String title;
        private String content;
        private String date;
        private String time;
        private String producer;
        private String vintage;
        private String country;
        private String grapes;

        Note(String title, String content, String date, String time, String producer, String vintage, String country, String grapes){
            this.title = title;
            this.content = content;
            this.date = date;
            this.time = time;
            this.producer = producer;
            this.vintage = vintage;
            this.country = country;
            this.grapes = grapes;
        }

        Note(long id,String title,String content,String date, String time, String producer, String vintage, String country, String grapes){
            this.id = id;
            this.title = title;
            this.content = content;
            this.date = date;
            this.time = time;
            this.producer = producer;
            this.vintage = vintage;
            this.country = country;
            this.grapes = grapes;
        }
        Note()
        {
            // empty constructor
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getVintage() {
            return vintage;
        }

        public void setVintage(String vintage) {
            this.vintage = vintage;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getGrapes() {
            return grapes;
        }

        public void setGrapes(String grapes) {
            this.grapes = grapes;
        }
    }



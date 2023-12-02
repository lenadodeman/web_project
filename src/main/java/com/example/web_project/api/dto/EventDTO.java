package com.example.web_project.api.dto;



import java.util.Date;
import java.util.List;


public class EventDTO {

        private long id;
        private Date date;
        private int valueEvent;
        private String comment;
        private SerieDTO serie;
        private List<TagDTO> tags;

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }

        public int getValueEvent() {
                return valueEvent;
        }

        public void setValueEvent(int valueEvent) {
                this.valueEvent = valueEvent;
        }

        public String getComment() {
                return comment;
        }

        public void setComment(String comment) {
                this.comment = comment;
        }

        public SerieDTO getSerie() {
                return serie;
        }

        public void setSerie(SerieDTO serie) {
                this.serie = serie;
        }

        public List<TagDTO> getTags() {
                return tags;
        }

        public void setTags(List<TagDTO> tags) {
                this.tags = tags;
        }


}

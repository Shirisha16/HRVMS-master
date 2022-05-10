package com.cstech.hrvms.Models;

public class HolidaysList {



        private int id;
        private String dateOfHoliday,dayOfWeeks,holiday,country;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDateOfHoliday() {
            return dateOfHoliday;
        }

        public void setDateOfHoliday(String dateOfHoliday) {
            this.dateOfHoliday = dateOfHoliday;
        }

        public String getDayOfWeeks() {
            return dayOfWeeks;
        }

        public void setDayOfWeeks(String dayOfWeeks) {
            this.dayOfWeeks = dayOfWeeks;
        }

        public String getHoliday() {
            return holiday;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "HolidaysList{" +
                    "id=" + id +
                    ", dateOfHoliday='" + dateOfHoliday + '\'' +
                    ", dayOfWeeks='" + dayOfWeeks + '\'' +
                    ", holiday='" + holiday + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }


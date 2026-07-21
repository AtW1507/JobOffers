package com.JobOffers;

public interface SampleJobOfferResponse {

    default String bodyWithZeroOffersJson() {
        return "[]";
    }

    default String bodyWithTwoOffersJson() {
        return """
                [
                  {
                    "title": "Junior Java Developer",
                    "company": "Connectis_",
                    "salary": "14 000 – 17 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-connectis--warszawa",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                  },
                  {
                    "title": "Junior Java Developer (f/m)",
                    "company": "Netcompany Poland",
                    "salary": "8 000 – 9 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-f-m-netcompany-poland-warsaw",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                  }
                  ]""".trim();
    }

    default String bodyWithOneOfferJson(){
        return """
                [
                {
                    "title": "Junior Java Developer",
                    "company": "Connectis_",
                    "salary": "14 000 – 17 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-connectis--warszawa",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                }
                ]""".trim();
    }

    default String bodyWithFourOffersJson(){
        return """
                [
                {
                    "title": "Junior Java Developer",
                    "company": "Connectis_",
                    "salary": "14 000 – 17 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-connectis--warszawa",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                  },
                  {
                    "title": "Junior Java Developer (f/m)",
                    "company": "Netcompany Poland",
                    "salary": "8 000 – 9 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-f-m-netcompany-poland-warsaw",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                  },
                  {
                    "title": "Junior Backend Developer",
                    "company": "Kalamba Games",
                    "salary": "6 000 – 10 000 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/junior-backend-developer-kalamba-games-krakow",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                  },
                  {
                    "title": "(Not just a) Junior Software Engineer",
                    "company": "u2i",
                    "salary": "9 000 – 11 500 PLN",
                    "offerUrl": "https://nofluffjobs.com/pl/job/not-just-a-junior-software-engineer-u2i-krakow",
                    "source": "nofluffjobs",
                    "salary_estimated": false
                  }
                ]""".trim();
    }
}

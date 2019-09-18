package com.ermile.khadijehapp.Item;

public class item_Main {

    public static final int SLIDE= 100;
    public static final int LINK_1= 1;
    public static final int LINK_2 = 2;
    public static final int LINK_Desc = 3;
    public static final int LINK_4 = 4;
    public static final int TITEL_link = 10;
    public static final int TITEL_NONE = 11;
    public static final int SALAVAT = 20;
    public static final int HADITH = 21;
    public static final int NEWS  = 30;
    public static final int HR = 0;



    public int type;
    public String link1_url;
    public String link2_url_1,link2_url_2;
    public String link3_url_img,link3_title,link3_desc;
    public String link4_url_img1,link4_url_img2,link4_url_img3,link4_url_img4;
    public String titleLink_title,titleLink_go,titleLink_url;
    public String titleNONE_title;
    public String salavat_count,salavat_readText,salavat_text;
    public String hadith_title,hadith_desc;
    public String news_url_img,news_title,news_desc;
    public String hr_url_img;
    public String slide_image,slide_title,slide_desc;

    public item_Main(int type, String link1_url, String link2_url_1, String link2_url_2, String link3_url_img, String link3_title, String link3_desc, String link4_url_img1, String link4_url_img2, String link4_url_img3, String link4_url_img4, String titleLink_title, String titleLink_go, String titleLink_url, String titleNONE_title, String salavat_count, String salavat_readText, String salavat_text, String hadith_title, String hadith_desc, String news_url_img, String news_title, String news_desc, String hr_url_img, String slide_image, String slide_title, String slide_desc) {
        this.type = type;
        this.link1_url = link1_url;
        this.link2_url_1 = link2_url_1;
        this.link2_url_2 = link2_url_2;
        this.link3_url_img = link3_url_img;
        this.link3_title = link3_title;
        this.link3_desc = link3_desc;
        this.link4_url_img1 = link4_url_img1;
        this.link4_url_img2 = link4_url_img2;
        this.link4_url_img3 = link4_url_img3;
        this.link4_url_img4 = link4_url_img4;
        this.titleLink_title = titleLink_title;
        this.titleLink_go = titleLink_go;
        this.titleLink_url = titleLink_url;
        this.titleNONE_title = titleNONE_title;
        this.salavat_count = salavat_count;
        this.salavat_readText = salavat_readText;
        this.salavat_text = salavat_text;
        this.hadith_title = hadith_title;
        this.hadith_desc = hadith_desc;
        this.news_url_img = news_url_img;
        this.news_title = news_title;
        this.news_desc = news_desc;
        this.hr_url_img = hr_url_img;
        this.slide_image = slide_image;
        this.slide_title = slide_title;
        this.slide_desc = slide_desc;
    }
}

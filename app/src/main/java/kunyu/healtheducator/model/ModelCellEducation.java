package kunyu.healtheducator.model;

import com.orm.SugarRecord;

import java.util.Random;

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * Created by QuentinTsai on 17/04/2016.
 */
public class ModelCellEducation extends SugarRecord {
    public String imageUrl;

    public String textTitle;

    public String textContent;

    public Boolean isRead = false;

    public String webAddress;

    public int maxLine = 1;

    public static void spawnData(String[] webLinkArray, int spawnCount){
        LoremIpsum loremIpsum = new LoremIpsum();
        for( int i = 0 ; i < spawnCount; i++){
            ModelCellEducation modelCellEducation = new ModelCellEducation();
            modelCellEducation.imageUrl = "http://lorempixel.com/500/300/";
            modelCellEducation.textTitle = loremIpsum.getWords(100, i);
            modelCellEducation.textContent = loremIpsum.getWords(150, i);
            modelCellEducation.setMaxLine(new Random().nextInt(7-3) + 3);
            modelCellEducation.setWebAddress(webLinkArray[i%webLinkArray.length]);
            modelCellEducation.save();
        }
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public static long count(){
        return ModelCellEducation.count(ModelCellEducation.class);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}

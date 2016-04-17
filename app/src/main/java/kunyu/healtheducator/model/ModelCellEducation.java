package kunyu.healtheducator.model;

import com.orm.SugarRecord;
import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * Created by QuentinTsai on 17/04/2016.
 */
public class ModelCellEducation extends SugarRecord {
    public String imageUrl;

    public String textTitle;

    public String textContent;

    public Boolean isRead = false;

    public static void spawnData(int spawnCount){
        LoremIpsum loremIpsum = new LoremIpsum();

        while(spawnCount > 0){
            ModelCellEducation modelCellEducation = new ModelCellEducation();
            modelCellEducation.imageUrl = "http://lorempixel.com/400/200/";
            modelCellEducation.textTitle = loremIpsum.getWords(30);
            modelCellEducation.textContent = loremIpsum.getParagraphs();
            modelCellEducation.save();
            spawnCount--;
        }
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

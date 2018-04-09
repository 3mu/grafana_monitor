package monitor.utils;

import java.util.List;

public class ActionCard {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    private String singleTitle;

    public String getSingleTitle() {
        return singleTitle;
    }

    public void setSingleTitle(String singleTitle) {
        this.singleTitle = singleTitle;
    }

    private String singleURL;

    public String getSingleURL() {
        return singleURL;
    }

    public void setSingleURL(String singleURL) {
        this.singleURL = singleURL;
    }

    private String hideAvatar;

    public String getHideAvatar() {
        return hideAvatar;
    }

    public void setHideAvatar(String hideAvatar) {
        this.hideAvatar = hideAvatar;
    }

    private String btnOrientation;

    public String getBtnOrientation() {
        return btnOrientation;
    }

    public void setBtnOrientation(String btnOrientation) {
        this.btnOrientation = btnOrientation;
    }

    private List<Button> btns;

    public List<Button> getBtns() {
        return btns;
    }

    public void setBtns(List<Button> btns) {
        this.btns = btns;
    }
}


package durithon.wearableduri;

import android.support.v7.app.AppCompatActivity;

public class Data extends AppCompatActivity {
    public int image;
    public String title;
    public String artist;
    public boolean hart;
    public int hartcount;
    public boolean now_play;
    public int sound;
    public boolean alpha;
    public String tag;
    public Data(int image, String title, String artist, boolean hart, int hartcount, boolean now_play, int sound, boolean alpha){
        this.image = image;
        this.title = title;
        this.artist = artist;
        this.hart = hart;
        this.hartcount = hartcount;
        this.sound = sound;
        this.now_play = now_play;
        this.alpha = alpha;
//        this.tag= tag;

    }

}

package xyz.qa;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int WC= ViewGroup.LayoutParams.WRAP_CONTENT;
    private static int MP= ViewGroup.LayoutParams.MATCH_PARENT;
    private TextView questionText;
    private TextView answerText;
    private boolean isExpand=false;
    private ImageView expandView;
    private int durationMills=618;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        /**整体布局**/
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(WC,WC);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout,lp);

        /**问题栏**/
        RelativeLayout questionBar=new RelativeLayout(this);
        RelativeLayout.LayoutParams rp=new RelativeLayout.LayoutParams(MP,WC);
        questionBar.setBackgroundColor(Color.LTGRAY);
        linearLayout.addView(questionBar,rp);

        //问题文本
        questionText=new TextView(this);
        rp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rp.addRule(RelativeLayout.CENTER_VERTICAL);
        rp.leftMargin=32;
        questionText.setText("1.有什么问题？");
        questionBar.addView(questionText,rp);

        //旋转箭头
        expandView =new ImageView(this);
        rp=new  RelativeLayout.LayoutParams(118,118);
        expandView.setBackgroundResource(R.drawable.a);
        rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rp.addRule(RelativeLayout.CENTER_VERTICAL);
        questionBar.addView(expandView,rp);

        //回答文本
        answerText =new TextView(this);
        lp=new LinearLayout.LayoutParams(WC, WC);
        answerText.setText("　　经常在APP中能看到有引用文章或大段博文的内容，他们的展示样式也有点儿意思，默认是折叠的，当你点击文章之后它会自动展开。再次点击他又会缩回去。 \n" +
                "　　网上有找到部分效果，感觉不是很满意。最后自己尝试用 自定义布局layout 写了个demo。比较简陋，不过可以用了。有这方面需求的朋友可以稍加改造下。如有更好的创意，也不妨分享一下。 ");
        answerText.setHeight(0);
        linearLayout.addView(answerText,lp);
        questionBar.setOnClickListener(onClickListener);



    }


    //监听
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int textHight= answerText.getLineHeight()* answerText.getLineCount();

            //本来就已经展开了的情况
            if(isExpand) {

                /**箭头旋转动画**/
                RotateAnimation arrowAnimation=new RotateAnimation(-90,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                arrowAnimation.setDuration(durationMills);
                arrowAnimation.setFillAfter(true);
                expandView.startAnimation(arrowAnimation);

                /**文字动画**/
                Animation textAnimation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        super.applyTransformation(interpolatedTime, t);
                        answerText.setHeight((int) (textHight - textHight * interpolatedTime));
                    }

                };
                textAnimation.setDuration(durationMills);
                answerText.startAnimation(textAnimation);
                isExpand=false;

                //本来就已经收起了的情况
            }else {
                /**箭头旋转动画**/
                RotateAnimation arrowAnimation=new RotateAnimation(0,-90, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                arrowAnimation.setDuration(durationMills);
                arrowAnimation.setFillAfter(true);
                expandView.startAnimation(arrowAnimation);

                /**文字动画**/
                Animation TextAnimation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        super.applyTransformation(interpolatedTime, t);
                        answerText.setHeight((int) (textHight*interpolatedTime));
                    }

                };
                TextAnimation.setDuration(durationMills);
                answerText.startAnimation(TextAnimation);
                isExpand=true;
            }



        }
    };
}

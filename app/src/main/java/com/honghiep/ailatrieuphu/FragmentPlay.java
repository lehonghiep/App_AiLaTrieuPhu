package com.honghiep.ailatrieuphu;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.honghiep.ailatrieuphu.models.Question;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by honghiep on 15/07/2017.
 */

public class FragmentPlay extends Fragment implements View.OnClickListener, CustomDialogYesNo.ICustomDialogYesNo, CustomDiglogOk.ICustomDiglogOk {
    private Question question;
    private IQuestions mInterf;
    private CustomDialogYesNo.ICustomDialogYesNo dialogYesNo;
    private TextView tv_money;
    private TextView tv_time;
    private AsyncTaskEventTime eventTime;
    private Executor executorTime;
    private Executor executorButtonAnswer;
    private Context context;
    private View viewButtonTrueCase;
    private Button btn_a, btn_b, btn_c, btn_d;
    private Button btn_pause, btn_fifty, btn_khangia, btn_call, btn_switch;
    private int trueCase;
    private int chooserCase;
    private AsytastEventButtonAnswer asytastEventButtonAnswer;
    private Random random = new Random();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mInterf = (IQuestions) context;
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executorTime = Executors.newFixedThreadPool(1);
        executorButtonAnswer = Executors.newFixedThreadPool(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        question = ((MainActivity) getActivity()).getData();
        trueCase = question.getTrueCase();

        TextView tv_ask = view.findViewById(R.id.tv_question);
        tv_ask.setText("Câu hỏi số " + question.getLevel() + ": " + question.getAsk());
        btn_a = view.findViewById(R.id.btn_a);
        btn_a.setText("A." + question.getRa());
        btn_b = view.findViewById(R.id.btn_b);
        btn_b.setText("B." + question.getRb());
        btn_c = view.findViewById(R.id.btn_c);
        btn_c.setText("C." + question.getRc());
        btn_d = view.findViewById(R.id.btn_d);
        btn_d.setText("D." + question.getRd());
        btn_a.setOnClickListener(this);
        btn_b.setOnClickListener(this);
        btn_c.setOnClickListener(this);
        btn_d.setOnClickListener(this);

        btn_pause = view.findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(this);

        if (mInterf.getClickButtonFifty()) {
            btn_fifty = view.findViewById(R.id.btn_fifty);
            btn_fifty.setBackgroundResource(R.drawable.fifty);
            btn_fifty.setOnClickListener(this);
        }
        if (mInterf.getClickButtonKhanGia()) {
            btn_khangia = view.findViewById(R.id.btn_khangia);
            btn_khangia.setBackgroundResource(R.drawable.khangia);
            btn_khangia.setOnClickListener(this);
        }
        if (mInterf.getClickButtonCall()) {
            btn_call = view.findViewById(R.id.btn_call);
            btn_call.setBackgroundResource(R.drawable.goidien);
            btn_call.setOnClickListener(this);
        }
        if (mInterf.getClickButtonSwith()) {
            btn_switch = view.findViewById(R.id.btn_switch);
            btn_switch.setBackgroundResource(R.drawable.switch2);
            btn_switch.setOnClickListener(this);
        }

        SounManager.startSoundQuestion(context, question.getLevel());

        tv_money = view.findViewById(R.id.tv_money);
        tv_money.setText("$" + getMoney(question));
        tv_time = view.findViewById(R.id.tv_time);
        eventTime = new AsyncTaskEventTime();
        eventTime.execute(59);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pause:
                ((MainActivity) getActivity()).onBackPressed();
                break;
            case R.id.btn_fifty:
                onClickButtonHelp(view);
                break;
            case R.id.btn_khangia:
                onClickButtonHelp(view);
                break;
            case R.id.btn_call:
                onClickButtonHelp(view);
                break;
            case R.id.btn_switch:
                onClickButtonHelp(view);
                break;
            default:
                viewButtonTrueCase = view;
                showCustomDialogYesNo(view);
                break;
        }
    }

    private synchronized void eventChooserAnswer(View view) {

        switch (view.getId()) {
            case R.id.btn_a:
                btn_a.setBackgroundResource(R.drawable.case3);
                chooserCase = 1;
                break;
            case R.id.btn_b:
                btn_b.setBackgroundResource(R.drawable.case3);
                chooserCase = 2;
                break;
            case R.id.btn_c:
                btn_c.setBackgroundResource(R.drawable.case3);
                chooserCase = 3;
                break;
            case R.id.btn_d:
                btn_d.setBackgroundResource(R.drawable.case3);
                chooserCase = 4;
                break;
            default:
                break;
        }
        destroyTime();
        asytastEventButtonAnswer = new AsytastEventButtonAnswer();
        asytastEventButtonAnswer.executeOnExecutor(executorButtonAnswer);
    }

    public void destroyTime() {
        if (eventTime != null) {
            eventTime.isRunning = false;
            eventTime.cancel(true);
        }
    }

    public void destroyAnimAnswer() {
        if (asytastEventButtonAnswer != null) {
            asytastEventButtonAnswer.cancel(true);
        }
    }

    @Override
    public void onClickAgree() {
        eventChooserAnswer(viewButtonTrueCase);
    }

    public void showCustomDialogYesNo(View view) {
        CustomDialogYesNo dialogYesNo =
                new CustomDialogYesNo(context, R.style.Stylelogo, this);
        if (view.getId() == R.id.btn_a) {
            dialogYesNo.setTextNotify("Đáp án cuối cùng của bạn là A");
        } else if (view.getId() == R.id.btn_b) {
            dialogYesNo.setTextNotify("Đáp án cuối cùng của bạn là B");
        } else if (view.getId() == R.id.btn_c) {
            dialogYesNo.setTextNotify("Đáp án cuối cùng của bạn là C");
        } else {
            dialogYesNo.setTextNotify("Đáp án cuối cùng của bạn là D");
        }
        dialogYesNo.show();

    }

    @Override
    public void onClickOk() {
        ((MainActivity) getActivity()).openChooser();
    }

    public class AsyncTaskEventTime extends AsyncTask<Integer, Integer, String> {
        private boolean isRunning = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            int i = integers[0];
            while (isRunning == true && i >= 0) {
                publishProgress(i);
                i--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv_time.setText("0:" + values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SounManager.playSounOutTime(context);
            notifyGameOver();
        }
    }

    public class AsytastEventButtonAnswer extends AsyncTask<Integer, Integer, Boolean> {
        private int trueCaseInSy;
        private int chooserCaseInSy;
        private AnimationDrawable drawableWrong, drawableCorrect;

        @Override
        protected void onPreExecute() {
            trueCaseInSy = trueCase;
            chooserCaseInSy = chooserCase;
            setEnableFalseAllButton();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            try {
                Thread.sleep(SounManager.playSoundChooser(context, chooserCaseInSy, question.getLevel()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
            try {
                Thread.sleep(SounManager.startSoundResult(context,trueCase,trueCaseInSy == chooserCaseInSy));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return trueCaseInSy == chooserCaseInSy;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (trueCaseInSy != chooserCaseInSy) {
                switch (chooserCase) {
                    case 1:
                        btn_a.setBackgroundResource(R.drawable.animation_wrong);
                        drawableWrong = (AnimationDrawable) btn_a.getBackground();
                        break;
                    case 2:
                        btn_b.setBackgroundResource(R.drawable.animation_wrong);
                        drawableWrong = (AnimationDrawable) btn_b.getBackground();
                        break;
                    case 3:
                        btn_c.setBackgroundResource(R.drawable.animation_wrong);
                        drawableWrong = (AnimationDrawable) btn_c.getBackground();
                        break;
                    case 4:
                        btn_d.setBackgroundResource(R.drawable.animation_wrong);
                        drawableWrong = (AnimationDrawable) btn_d.getBackground();
                        break;
                    default:
                        break;
                }
                if (drawableWrong != null) {
                    drawableWrong.start();
                }
            }
            switch (trueCase) {
                case 1:
                    btn_a.setBackgroundResource(R.drawable.animation_true);
                    drawableCorrect = (AnimationDrawable) btn_a.getBackground();
                    break;
                case 2:
                    btn_b.setBackgroundResource(R.drawable.animation_true);
                    drawableCorrect = (AnimationDrawable) btn_b.getBackground();
                    break;
                case 3:
                    btn_c.setBackgroundResource(R.drawable.animation_true);
                    drawableCorrect = (AnimationDrawable) btn_c.getBackground();
                    break;
                case 4:
                    btn_d.setBackgroundResource(R.drawable.animation_true);
                    drawableCorrect = (AnimationDrawable) btn_d.getBackground();
                    break;
                default:
                    break;
            }
            if (drawableCorrect != null) {
                drawableCorrect.start();
            }

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean check) {
            super.onPostExecute(check);
            if (check) {
                if (mInterf.countQuestion() > 0) {
                    mInterf.openPlay();
                } else {
                    CustomDiglogOk diglogOk = new CustomDiglogOk(context, R.style.Stylelogo, FragmentPlay.this);
                    diglogOk.setTextNotify("Số tiền của bạn là: $150000");
                    diglogOk.setCancelable(false);
                    diglogOk.show();
                }
            } else {
                if (drawableWrong != null) {
                    drawableWrong.stop();
                }
                if (drawableCorrect != null) {
                    drawableCorrect.stop();
                }
                notifyGameOver();
            }
        }
    }

    public void notifyGameOver() {
        CustomDiglogOk diglogOk = new CustomDiglogOk(context, R.style.Stylelogo, FragmentPlay.this);
        diglogOk.setTextNotify("Số tiền của bạn là: " + tv_money.getText());
        diglogOk.setCancelable(false);
        diglogOk.show();
    }

    public int getMoney(Question question) {
        int level = question.getLevel();
        if (level < 2) {
            return 0;
        }
        if (level < 3) {
            return 200;
        }
        if (level < 4) {
            return 400;
        }
        if (level < 5) {
            return 600;
        }
        if (level < 6) {
            return 1000;
        }
        if (level < 7) {
            return 2000;
        }
        if (level < 8) {
            return 3000;
        }
        if (level < 9) {
            return 6000;
        }
        if (level < 10) {
            return 10000;
        }
        if (level < 11) {
            return 14000;
        }
        if (level < 12) {
            return 22000;
        }
        if (level < 13) {
            return 30000;
        }
        if (level < 14) {
            return 40000;
        }
        if (level < 15) {
            return 60000;
        }
        return 85000;
    }

    public void onClickButtonHelp(final View view) {
        CustomDialogYesNo dialogYesNo = new CustomDialogYesNo(context, R.style.StypeDialog, new CustomDialogYesNo.ICustomDialogYesNo() {
            @Override
            public void onClickAgree() {
                view.setEnabled(false);
                switch (view.getId()) {
                    case R.id.btn_fifty:
                        view.setBackgroundResource(R.drawable.fifty2);
                        mInterf.setClickButtonFifty(false);
                        eventFifty();
                        break;
                    case R.id.btn_khangia:
                        view.setBackgroundResource(R.drawable.khangia2);
                        mInterf.setClickButtonKhanGia(false);
                        break;
                    case R.id.btn_call:
                        view.setBackgroundResource(R.drawable.call2);
                        mInterf.setClickButtonCall(false);
                        eventCallFriends();
                        break;
                    case R.id.btn_switch:
                        view.setBackgroundResource(R.drawable.switch3);
                        mInterf.setClickButtonSwith(false);
                        mInterf.changeQuestionByIdAndLevel(question.getLevel(), question.getQuestionID());
                        break;
                }
            }
        });
        dialogYesNo.setTextNotify("Bạn muốn sử dụng sự trợ giúp này");
        dialogYesNo.show();
    }

    private void eventCallFriends() {
        AsyncTask<Void, Void, Boolean>syHelp=new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                setEnableFalseAllButton();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                SystemClock.sleep(SounManager.playSoundCallFriends(context,question.getLevel()));
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                new CustomDialogGoiNguoiThan(context, R.style.StypeDialog,
                        new CustomDialogGoiNguoiThan.ICustomDialogGoiNguoiThan() {
                            @Override
                            public void onClickFriends(View view) {
                                switch (view.getId()) {
                                    case R.id.iv_ngobaochau:
                                        eventShowDapAn(94);
                                        break;
                                    case R.id.iv_anhxtanh:
                                        eventShowDapAn(89);
                                        break;
                                    case R.id.iv_levanlan:
                                        eventShowDapAn(84);
                                        break;
                                    case R.id.iv_khongminh:
                                        eventShowDapAn(79);
                                        break;
                                    case R.id.iv_billgate:
                                        eventShowDapAn(74);
                                        break;
                                    case R.id.iv_markzukerbeg:
                                        eventShowDapAn(69);
                                        break;
                                }
                            }
                        }).show();
                setEnableTrueAllButton();
            }
        };
        syHelp.executeOnExecutor(Executors.newFixedThreadPool(1));
    }

    private void eventFifty() {
        AsyncTask<Void, Void, Boolean>syHelp=new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                setEnableFalseAllButton();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                SystemClock.sleep(SounManager.playSound5050(context,question.getLevel()));
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                int falseCaseOne = random.nextInt(4) + 1;
                while (trueCase == falseCaseOne) {
                    falseCaseOne = random.nextInt(4) + 1;
                }
                int falseCaseTwo = random.nextInt(4) + 1;
                while (trueCase == falseCaseTwo || falseCaseTwo == falseCaseOne) {
                    falseCaseTwo = random.nextInt(4) + 1;
                }
                if (falseCaseOne == 1 || falseCaseTwo == 1) {
                    btn_a.setText("");
                }
                if (falseCaseOne == 2 || falseCaseTwo == 2) {
                    btn_b.setText("");
                }
                if (falseCaseOne == 3 || falseCaseTwo == 3) {
                    btn_c.setText("");
                }
                if (falseCaseOne == 4 || falseCaseTwo == 4) {
                    btn_d.setText("");
                }

                setEnableTrueAllButton();
            }
        };
        syHelp.executeOnExecutor(Executors.newFixedThreadPool(1));

    }

    private void setEnableTrueAllButton() {
        btn_a.setEnabled(true);
        btn_b.setEnabled(true);
        btn_c.setEnabled(true);
        btn_d.setEnabled(true);

        btn_pause.setEnabled(true);
        if (btn_fifty != null) {
            btn_fifty.setEnabled(true);
        }
        if (btn_khangia != null) {
            btn_khangia.setEnabled(true);
        }
        if (btn_call != null) {
            btn_call.setEnabled(true);
        }
        if (btn_switch != null) {
            btn_switch.setEnabled(true);
        }
    }

    private void setEnableFalseAllButton() {
        btn_a.setEnabled(false);
        btn_b.setEnabled(false);
        btn_c.setEnabled(false);
        btn_d.setEnabled(false);

        btn_pause.setEnabled(false);
        if (btn_fifty != null) {
            btn_fifty.setEnabled(false);
        }
        if (btn_khangia != null) {
            btn_khangia.setEnabled(false);
        }
        if (btn_call != null) {
            btn_call.setEnabled(false);
        }
        if (btn_switch != null) {
            btn_switch.setEnabled(false);
        }
    }

    private void eventShowDapAn(int percentCorrect) {
        int dapan;
        if (random.nextInt(100) > percentCorrect) {
            dapan = random.nextInt(4) + 1;
        } else {
            dapan = trueCase;
        }
        CustomDialogDapan dialogDapan =
                new CustomDialogDapan(context, R.style.StypeDialog);
        dialogDapan.setTextNotify(convertAnswertoString(dapan));
        dialogDapan.show();
    }

    private String convertAnswertoString(int caseNumber) {
        return (char) (caseNumber + 48 + 16) + "";
    }
}

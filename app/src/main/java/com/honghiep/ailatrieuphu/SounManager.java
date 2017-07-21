package com.honghiep.ailatrieuphu;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by honghiep on 21/07/2017.
 */

public class SounManager {
    private static MediaPlayer mediaPlayer;
    private static AsyncTask<Void, Void, Boolean> sy;
    private static Executor executor = Executors.newFixedThreadPool(1);

    public static void startSoundQuestion(final Context context, final int level) {
        destroySound();
        if (level == 1) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques01);
        } else if (level == 2) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques02);
        } else if (level == 3) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques03);
        } else if (level == 4) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques04);
        } else if (level == 5) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques05);
        } else if (level == 6) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques06);
        } else if (level == 7) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques07);
        } else if (level == 8) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques08);
        } else if (level == 9) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques09);
        } else if (level == 10) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques10);
        } else if (level == 11) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques11);
        } else if (level == 12) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques12);
        } else if (level == 13) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques13);
        } else if (level == 14) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques14);
        } else {
            mediaPlayer = MediaPlayer.create(context, R.raw.ques15);
        }
        mediaPlayer.start();
        final long timeMedia = mediaPlayer.getDuration();

        playSoundPosition(context, level, timeMedia);
    }

    private static void playSoundPosition(final Context context, final int level, final long timeMedia) {
        sy = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                SystemClock.sleep(timeMedia);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                destroySound();
                if (level <= 5) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.moc1);
                } else if (level <= 10) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.moc2);
                } else {
                    mediaPlayer = MediaPlayer.create(context, R.raw.moc3);
                }
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        };
        sy.executeOnExecutor(executor);
    }

    public static void destroySound() {
        if (sy != null) {
            sy.cancel(true);
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public static long playSoundChooser(final Context context, int caseChoose, final int level) {
        destroySound();
        if (caseChoose == 1) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ans_a);
        } else if (caseChoose == 2) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ans_b);
        } else if (caseChoose == 3) {
            mediaPlayer = MediaPlayer.create(context, R.raw.ans_c);
        } else {
            mediaPlayer = MediaPlayer.create(context, R.raw.ans_d);
        }
        mediaPlayer.start();
        if (level > 5) {


            final long timeMedia = mediaPlayer.getDuration();
            sy = new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    SystemClock.sleep(timeMedia);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    destroySound();
                    if (new Random().nextBoolean()) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.ans_now1);
                    } else {
                        mediaPlayer = MediaPlayer.create(context, R.raw.ans_now2);
                    }
                    mediaPlayer.start();
                }
            };
            sy.executeOnExecutor(executor);
            return mediaPlayer.getDuration() + timeMedia;
        }
        return mediaPlayer.getDuration();
    }

    public static long startSoundResult(Context context, int trueCase, boolean isCorrect) {
        destroySound();
        if (isCorrect) {
            switch (trueCase) {
                case 1:
                    mediaPlayer = MediaPlayer.create(context, R.raw.true_a);
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(context, R.raw.true_b);
                    break;
                case 3:
                    mediaPlayer = MediaPlayer.create(context, R.raw.true_c);
                    break;
                default:
                    mediaPlayer = MediaPlayer.create(context, R.raw.true_d);
                    break;
            }
        } else {
            switch (trueCase) {
                case 1:
                    mediaPlayer = MediaPlayer.create(context, R.raw.lose_a);
                    break;
                case 2:
                    mediaPlayer = MediaPlayer.create(context, R.raw.lose_b);
                    break;
                case 3:
                    mediaPlayer = MediaPlayer.create(context, R.raw.lose_c);
                    break;
                default:
                    mediaPlayer = MediaPlayer.create(context, R.raw.lose_d);
                    break;
            }
        }
        mediaPlayer.start();
        return mediaPlayer.getDuration();
    }

    public static void playSounOutTime(Context context) {
        destroySound();
        mediaPlayer = MediaPlayer.create(context, R.raw.out_of_time);
        mediaPlayer.start();
    }

    public static long playSound5050(final Context context, final int level) {
        destroySound();
        mediaPlayer = MediaPlayer.create(context, R.raw.sound5050);
        mediaPlayer.start();
        final long timeMedia = mediaPlayer.getDuration();
        playSoundPosition(context,level,timeMedia);
        return timeMedia;
    }
    public static long playSoundCallFriends(final Context context, final int level) {
        destroySound();
        mediaPlayer = MediaPlayer.create(context, R.raw.call);
        mediaPlayer.start();
        final long timeMedia = mediaPlayer.getDuration();
        playSoundPosition(context,level,timeMedia);
        return timeMedia;
    }
    public static void playSoundBackGroud(Context context){
        destroySound();
        mediaPlayer=MediaPlayer.create(context,R.raw.background_music);
        mediaPlayer.start();
    }
    public static void playSoundIsReady(Context context){
        destroySound();
        Context context1=context;
        mediaPlayer=MediaPlayer.create(context,R.raw.ready);
        mediaPlayer.start();
    }
    public static void playSoundLose(Context context){
        destroySound();
        Context context1=context;
        mediaPlayer=MediaPlayer.create(context,R.raw.lose);
        mediaPlayer.start();
    }
}

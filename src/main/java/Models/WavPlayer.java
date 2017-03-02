package Models;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Екатериона  on 2/28/17.
 */

public class WavPlayer {

    private InputStream inputStream;
    private Vector<LineListener> listeners = new Vector<LineListener>();
    private SourceDataLine sourceDataLine = null;
    private boolean stop = false;
    private boolean pause = false;
    private int startSample;
    private int sampleWhenPlayStarted = -1;

    // Конструктор
    public WavPlayer (File file) throws IOException {
        inputStream = new BufferedInputStream(new FileInputStream(file));
    }

    public void addLineListener (LineListener l) {
        if (l != null && ! listeners.contains(l)) {
            listeners.add(l);
        }
    }


    public int getAudioFrame () {
        if (sourceDataLine == null) return 0;
        return sourceDataLine.getFramePosition()+sampleWhenPlayStarted;
    }


    public void play(int startSample) {
        this.startSample = startSample;
        new Thread() {
            @Override
            public void run() {

                int samplesRead = 0;

                AudioInputStream audioInputStream = null;
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(inputStream);
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    AudioFormat audioFormat = audioInputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
                    sourceDataLine.open(audioFormat);
                    Enumeration<LineListener> en = listeners.elements();
                    while (en.hasMoreElements()) {
                        sourceDataLine.addLineListener(en.nextElement());
                    }
                }
                catch (LineUnavailableException e) {
                    e.printStackTrace();
                    return;
                }

                sourceDataLine.start();
  //              byte[] data = new byte[524288];// 128Kb
                byte[] data = new byte[65536];// 128Kb
                try {
                    int bytesRead = 0;
                    while (bytesRead != -1) {
                        if (stop) {
                            sourceDataLine.stop();
                            break;
                        }

                        if (pause) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {}
                            continue;
                        }
                        bytesRead = audioInputStream.read(data, 0, data.length);
                        if (bytesRead >= 0) {
                            samplesRead += bytesRead;

                            if (samplesRead >= WavPlayer.this.startSample) {
                                if (sampleWhenPlayStarted < 0) {
                                    sampleWhenPlayStarted = (samplesRead-bytesRead)/4;
                                }
                                sourceDataLine.write(data, 0, bytesRead);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } finally {
                    sourceDataLine.close();
                }
            }
        }.start();
    }

    public void stop () {
        stop = true;
    }

    public void pause () {
        pause = !pause;
    }
}

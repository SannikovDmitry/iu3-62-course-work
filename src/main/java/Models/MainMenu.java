package Models;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Екатериона  on 2/28/17.
 */

public class MainMenu extends JFrame {

    private WavPlayer wavPlayer;

    private JPanel rootPanel;
    private JLabel copyright;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton chorusButton;
    private JButton distortionButton;

    private JLabel slider1Label;
    private JSlider slider1;
    private JLabel slider2Label;
    private JLabel slider3Label;
    private JLabel slider4Label;
    private JLabel slider5Label;
    private JLabel slider6Label;
    private JSlider slider2;
    private JSlider slider3;
    private JSlider slider4;
    private JSlider slider5;
    private JSlider slider6;
    private JButton openButton;
    private File file;

    private JFileChooser fileChooser;

    public MainMenu() {
        super("Курсовая работа. Максименко Екатерина.");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        fileChooser = new JFileChooser();

        playButton.setEnabled(false);

        // Слайдер 1. Вывод значения в Label1.
        slider1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider slider = (JSlider)changeEvent.getSource();
                if (!slider.getValueIsAdjusting()) {
                    slider1Label.setText(String.valueOf(slider.getValue()));
                }
            }
        });
        // Слайдер 2. Вывод значения в Label2.
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider slider = (JSlider)changeEvent.getSource();
                if (!slider.getValueIsAdjusting()) {
                    slider2Label.setText(String.valueOf(slider.getValue()));
                }
            }
        });
        // ...
        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider slider = (JSlider)changeEvent.getSource();
                if (!slider.getValueIsAdjusting()) {
                    slider3Label.setText(String.valueOf(slider.getValue()));
                }
            }
        });
        slider4.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider slider = (JSlider)changeEvent.getSource();
                if (!slider.getValueIsAdjusting()) {
                    slider4Label.setText(String.valueOf(slider.getValue()));
                }
            }
        });
        slider5.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider slider = (JSlider)changeEvent.getSource();
                if (!slider.getValueIsAdjusting()) {
                    slider5Label.setText(String.valueOf(slider.getValue()));
                }
            }
        });
        slider6.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider slider = (JSlider)changeEvent.getSource();
                if (!slider.getValueIsAdjusting()) {
                    slider6Label.setText(String.valueOf(slider.getValue()));
                }
            }
        });

        // Лисенер кнопки открытия файла
        openButton.addActionListener(new ActionListener() {
            // Перегружаем метод
            @Override
            // Слушаем событие
            public void actionPerformed(ActionEvent actionEvent) {
                // Если эта кнопка нажата, то
                if (actionEvent.getSource() == openButton) {

                    // Открываем диалог поиска файла
                    int returnVal = fileChooser.showOpenDialog(MainMenu.this);

                    // Если файл был выбран
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        // Назначаем переменной FILE file выбранную песню (путь)
                        file = fileChooser.getSelectedFile();
                        playButton.setEnabled(true);
                    }
                }
            }
        });

        // Кнопка запуска песни
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                // Если файла нет, то гг
                if (file != null) {
                    try {
                        // Назначаем экземлпяр класса проигрыв
                        wavPlayer = new WavPlayer(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Начинаем песню с самого начала (нулевой Sample)
                wavPlayer.play(-1);

                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
            }

        });
        // Кнопка остановки
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Вызов метода остановки
                wavPlayer.stop();
                playButton.setEnabled(true);
            }
        });
        // Кнопка паузы
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                wavPlayer.pause();
            }
        });
    }
}

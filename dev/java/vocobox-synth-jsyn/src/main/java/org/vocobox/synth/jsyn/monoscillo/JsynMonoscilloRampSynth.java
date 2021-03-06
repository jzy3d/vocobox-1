package org.vocobox.synth.jsyn.monoscillo;

import javax.swing.JPanel;

import org.vocobox.model.synth.VocoSynth;
import org.vocobox.synth.jsyn.JsynVocoSynthAbstract;

import com.jsyn.JSyn;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitSource;

/**
 * Ramps are used to make progressive change instead of abrupt changes in amplitude and frequency.
 * 
 */
public class JsynMonoscilloRampSynth extends JsynVocoSynthAbstract implements VocoSynth {
    UnitOscillator oscillo;

    LinearRamp frequencyRamp;
    LinearRamp amplitudeRamp;

    public JsynMonoscilloRampSynth() {
        wire();
    }

    @Override
    public void wire() {
        initSynthetizer();
        wireOscillatorToLineOut();
        defaultSettings();
    }
    
    /** Connect the oscillator to both channels of the output. */
    public void wireOscillatorToLineOut() {
        oscillo.output.connect(0, lineOut.input, 0);
        oscillo.output.connect(0, lineOut.input, 1);
    }


    public void initSynthetizer() {
        synth = JSyn.createSynthesizer();
        synth.add(oscillo = new SineOscillator());
        synth.add(lineOut = new LineOut());
        synth.add(frequencyRamp = makeFrequencyRamp(oscillo.frequency));
        synth.add(amplitudeRamp = makeAmplitudeRamp(oscillo.amplitude));
    }

    /** Set the frequency and amplitude for the sine wave. */
    public void defaultSettings() {
        oscillo.frequency.set(440.0);
        oscillo.amplitude.set(1);
        // osc.phase.set(.5);
    }


    @Override
    public void doSetAmplitude(float amplitude) {
        if(amplitudeRamp!=null){
            amplitudeRamp.input.set(amplitude);
        }
        else{
            oscillo.amplitude.set(amplitude);            
        }
    }


    @Override
    public void doSetFrequency(float frequency) {
        if(frequencyRamp!=null){
            frequencyRamp.input.set(frequency);
        }
        else{
            oscillo.frequency.set(frequency);            
        }
    }

    @Override
    public UnitOscillator getGenerator() {
        return oscillo;
    }

    @Override
    public UnitSource getSource() {
        return oscillo;
    }
    
    @Override
    public UnitOutputPort getOutput(){
        return oscillo.output;
    }

    @Override
    public JPanel newControlPanel() {
        return null;
    }
}

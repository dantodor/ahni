/*
 * Copyright (C) 2004 Derek James and Philip Tucker
 * 
 * This file is part of ANJI (Another NEAT Java Implementation).
 * 
 * ANJI is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 * 
 * created by Philip Tucker on Feb 22, 2003
 */
package ojc.ahni.hyperneat;

import java.io.IOException;

import ojc.ahni.hyperneat.HyperNEATTranscriberGridNet;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.jgapcustomised.ChromosomeMaterial;
import org.jgapcustomised.Configuration;
import org.jgapcustomised.IdFactory;
import org.jgapcustomised.InvalidConfigurationException;
import org.jgapcustomised.NaturalSelector;
import org.jgapcustomised.event.EventManager;
import org.jgapcustomised.impl.CloneReproductionOperator;
import org.jgapcustomised.impl.WeightedRouletteSelector;

import com.anji.integration.ActivatorTranscriber;
import com.anji.integration.SimpleSelector;
import com.anji.integration.Transcriber;
import com.anji.neat.NeatChromosomeUtility;
import com.anji.neat.NeatConfiguration;
import com.anji.nn.activationfunction.ActivationFunction;
import com.anji.util.Properties;
import com.anji.util.Randomizer;

/**
 * Extension of NEAT configuration with HyperNEAT-specific features added.
 * 
 * @author Oliver Coleman
 */
public class HyperNeatConfiguration extends NeatConfiguration {
    private static final Logger logger = Logger.getLogger(HyperNeatConfiguration.class);
    private Properties props;

    /**
     * See <a href=" {@docRoot}/params.htm" target="anji_params">Parameter Details </a> for
     * specific property settings.
     *
     * @param newProps configuration parameters; newProps[SURVIVAL_RATE_KEY] should be < 0.50f
     * @throws InvalidConfigurationException
     */
    public void init(Properties newProps) throws InvalidConfigurationException {
    	super.init(newProps);
        props = newProps;
        
        Transcriber transcriber = (Transcriber) props.singletonObjectProperty(ActivatorTranscriber.TRANSCRIBER_KEY);
        if (transcriber instanceof HyperNEATTranscriber) {
			short stimulusSize = ((HyperNEATTranscriber) transcriber).getCPPNInputCount();
			short responseSize = ((HyperNEATTranscriber) transcriber).getCPPNOutputCount();
    
			ChromosomeMaterial sample = NeatChromosomeUtility.newSampleChromosomeMaterial(
        		stimulusSize,
                props.getShortProperty(INITIAL_TOPOLOGY_NUM_HIDDEN_NEURONS_KEY, DEFAULT_INITIAL_HIDDEN_SIZE),
                responseSize,
                this,
                props.getBooleanProperty(INITIAL_TOPOLOGY_FULLY_CONNECTED_KEY, true));
	        setSampleChromosomeMaterial(sample);
        }
    }

    /**
     * See <a href=" {@docRoot}/params.htm" target="anji_params">Parameter Details </a> for
     * specific property settings.
     *
     * @param newProps
     * @see HyperNeatConfiguration#init(Properties)
     * @throws InvalidConfigurationException
     */
    public HyperNeatConfiguration(Properties newProps) throws InvalidConfigurationException {
        super(newProps);
    }
}

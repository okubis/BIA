package neuroevo;

import java.util.ArrayList;

import com.layer.SocketConnectionParameters;

import population.*;
import individual.*;
import ann.*;

import com.layer.SocketConnectionParameters;

import population.AbstractPopulationManager;
import population.Population;
import population.PopulationManager;

import java.util.ArrayList;
/**
 * Created by okubis on 5/26/16.
 */
public class Neat {
    private Population population;
    private PopulationManager populationManager;
    private ArrayList<ArrayList<Integer>> species; // change type as you wish


    //TODO: constructor;

    //TODO: metoda Evolve (i.e: hlavni metoda NEAT volana z main)
    //TODO: metodka pro rozrazeni do druhu;
    //TODO: metodka pro vyber turnajem - Druh
    //TODO: metodka pro vyber turnajem1 - Jedinec
    //TODO: metodka pro krizeni
    //TODO: metodka pro mutaci
    //TODO: metodka pro navrat nejlepsiho vysledku celeho behu NEATu


	
    private void divideIntoSpecies(){
    	this.species = new ArrayList<ArrayList<Integer>>();
    	ArrayList<Integer> best = new ArrayList<Integer>();
    	for (int i = 0; i < populationManager.getPopulationSize(); i++) {
			boolean neededNewSpecies = true;
			for (int j = 0; j < species.size(); j++) {
				Individual currentIndividual = population.getIndividualByItsPosition(i);
				if(currentIndividual.isSimilarTo(
						population.getIndividualByItsPosition(species.get(j).get(0)))){
					neededNewSpecies = false;
					species.get(j).add(i);
					if(currentIndividual.getFitness() > population.getIndividualByItsPosition(best.get(j)).getFitness()){
						best.set(j, i);
					}
					break;
				}
			}
			if(neededNewSpecies){
				species.add(new ArrayList<Integer>());
				species.get(species.size() - 1).add(i);
				best.add(i);
			}
		}
    	for (int i = 0; i < species.size(); i++) {
			for (int j = 0; j < species.get(i).size(); j++) {
				population.getIndividualByItsPosition(species.get(i).get(j)).setRepresentative(
						population.getIndividualByItsPosition(best.get(i)));
			}
		}
    	
    }
}

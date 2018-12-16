/*package com.assignment.noteapp;

import android.widget.Filter;

public class NoteFiilter extends Filter {
	
	

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		// TODO Auto-generated method stub
		
		
		
		FilterResults results = new FilterResults();
        // We implement here the filter logic
        if (constraint == null || constraint.length() == 0) {
            // No filter implemented we return all the list
            results.values = planetList;
            results.count = planetList.size();
        }
        else {
            // We perform filtering operation
            List<Planet> nPlanetList = new ArrayList<Planet>();

            for (Planet p : planetList) {
                if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                    nPlanetList.add(p);
            }

            results.values = nPlanetList;
            results.count = nPlanetList.size();

        }
        return results;
    
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		// TODO Auto-generated method stub
		
	}

}
*/
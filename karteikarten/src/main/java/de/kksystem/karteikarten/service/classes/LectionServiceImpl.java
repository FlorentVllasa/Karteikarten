package de.kksystem.karteikarten.service.classes;

import java.util.List;

import de.kksystem.karteikarten.facades.DaoFacade;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.service.interfaces.LectionService;

public class LectionServiceImpl implements LectionService {
	private DaoFacade daoFacade;
	
	public LectionServiceImpl() {
		daoFacade = DaoFacade.getInstance();
	}

	@Override
	public void delete(Lection lection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Lection findLection(int lectionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lection> findAllLection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveLection(Lection lection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Lection findLectionById(int lectionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countLections() {
		// TODO Auto-generated method stub
		return 0;
	}


}

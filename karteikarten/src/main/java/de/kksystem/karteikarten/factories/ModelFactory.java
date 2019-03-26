package de.kksystem.karteikarten.factories;

import de.kksystem.karteikarten.model.classes.CategoryImpl;
import de.kksystem.karteikarten.model.classes.FavoritelistImpl;
import de.kksystem.karteikarten.model.classes.IndexCardImpl;
import de.kksystem.karteikarten.model.classes.LectionImpl;
import de.kksystem.karteikarten.model.classes.PictureImpl;
import de.kksystem.karteikarten.model.classes.UserImpl;
import de.kksystem.karteikarten.model.interfaces.Category;
import de.kksystem.karteikarten.model.interfaces.Favoritelist;
import de.kksystem.karteikarten.model.interfaces.IndexCard;
import de.kksystem.karteikarten.model.interfaces.Lection;
import de.kksystem.karteikarten.model.interfaces.Picture;
import de.kksystem.karteikarten.model.interfaces.User;

public class ModelFactory {
	public static User createUser() {
		final User user = new UserImpl();
		return user;
	}
	
	public static Category createCategory() {
		final Category category = new CategoryImpl();
		return category;
	}
	
	public static Lection createLection() {
		final Lection lection = new LectionImpl();
		return lection;
	}
	
	public static IndexCard createIndexCard() {
		final IndexCard indexCard = new IndexCardImpl();
		return indexCard;
	}
	
	public static Picture createPicture() {
		final Picture picture = new PictureImpl();
		return picture;
	}
	
	public static Favoritelist createFavoritelist() {
		final Favoritelist favoritelist = new FavoritelistImpl();
		return favoritelist;
	}
}

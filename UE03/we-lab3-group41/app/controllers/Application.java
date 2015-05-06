package controllers;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import play.data.Form;
import play.data.format.Formatters;
import play.mvc.*;
import play.db.jpa.JPA;

import views.html.*;

import models.RealUser;

import java.text.ParseException;
import java.util.Locale;


public class Application extends Controller {

	public static Result index() {
		return ok(authentication.render());
	}


	public static Result registerPage() {
		return ok(registration.render(Form.form(RealUser.class)));
	}

	@play.db.jpa.Transactional
	public static Result register(){

		/*** Custom DataBinder ***/
		Formatters.register(Avatar.class, new Formatters.SimpleFormatter<Avatar>() {
			@Override
			public Avatar parse(String input, Locale arg1) throws ParseException {
				return Avatar.getAvatar(input);
			}

			@Override
			public String print(Avatar avatar, Locale arg1) {
				return avatar.toString();
			}
		});
		/*** Ends here ***/

		Form<RealUser> form = Form.form(RealUser.class).bindFromRequest();
		System.out.println(form.toString());
		if (form.hasErrors()) {
			return badRequest(registration.render(form));
		} else {
			RealUser realUser = form.get();
			JPA.em().persist(realUser);
		}
		return redirect(routes.Application.index());
	}


	public static Result signin(){
		/*TODO: Get and check input, somehow???*/
		if(true){
			return ok(jeopardy.render());
		}
		return unauthorized();
	}
}
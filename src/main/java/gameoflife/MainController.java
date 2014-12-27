package gameoflife;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

import freemarker.template.Configuration;

public class MainController {
	
	private static final Configuration conf = new Configuration();
	
    public static void main(String[] args) {
    	setConfig();
    	
        get("/", (req, res) -> {
        	return new ModelAndView(null,"main.ftl");
        	
        	}, new FreeMarkerEngine(conf));
        
        post("/board", "application/json", (req, res) -> {
        	int[][] board = fromJson(req.body()).getBoard();
        	
        	return new Game(board).nextGeneration();
        	
        	}, new JsonTransformer());
    }
    
    private static void setConfig(){
    	conf.setClassForTemplateLoading(MainController.class, "/template/");
    	conf.setLocalizedLookup(false);
    	staticFileLocation("/public");
    }
    
    private static Game fromJson(String json){
    	Gson gson = new Gson();
    	return gson.fromJson(json, Game.class);
    }
}

/**
 * 
 */
package particles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle implements Runnable {

    private ParticlesController controller;
    
    // taille (largeur et hauteur) de la particule
    private static final double SIZE = 10.0;

    // position de la particule (initialement : centre du panneau)
    private double x, y;

    // couleur de la particule (alÃ©atoire)
    private Color color;

    // vitesse de la particule (alÃ©atoire, entre -SIZE et SIZE)
    private double vx, vy;

    /**
     * constructeur
     * 
     * @param controller
     *            contrÃ´leur de l'application
     */
    public Particle(ParticlesController controller) {
    	this.controller=controller;
    	this.x=100;
    	this.y=100;
    	this.color=Color.color(Math.random(), Math.random(), Math.random());
    	this.vx= (Math.random() * (Particle.SIZE - (-Particle.SIZE) + 1)) + (-Particle.SIZE);
    	this.vy= (Math.random() * (Particle.SIZE - (-Particle.SIZE) + 1)) + (-Particle.SIZE);
    }

    /**
     * dessine la particule sur le Canvas du contrÃ´leur.
     * Cette mÃ©thode sera invoquÃ©eÃ©e par la mÃ©thode drawParticles du contrÃ´leur
     */
    public void draw() {
    	this.controller.getCanvas().getGraphicsContext2D().setFill(this.color);
    	this.controller.getCanvas().getGraphicsContext2D().fillOval(x, y, Particle.SIZE, Particle.SIZE);
    }

    /**
     * met Ã  jour la position de la particule
     */
    public void update() {
    	this.x = this.x + this.vx;
    	this.y = this.y + this.vy;
    }

    /**
     * retourne la visibilitÃ© de la particule (selon sa position, sa taille et
     * celle du canvas)
     * 
     * @return true si la particule est visible, false sinon
     */
    public boolean isVisible() {
        return !(this.x < 0 || this.x > 200 || this.y < 0 || this.y > 200);
    }

    /**
     * animation de la particule :
     *
     * Tant que la particule est visible
     *   - mise Ã  jour de la particule (mÃ©thode update)
     *   - dÃ©clencher le dessin de toutes les particules
     *   - "dormir" pendant 25 ms (Thread.sleep)
     *
     * Lorsque la particule n'est plus visible : 
     *   - retirer la particule du contrÃ´leur
     *   - ajouter une nouvelle particule au contrÃ´leur
     */
    public void run() {
    	if(!isVisible()){
    		this.controller.removeParticle(this);
    		this.controller.addParticle();
    	}
    	else{
    		try {
			Thread.sleep(25);
    		this.update();
    		this.controller.drawParticles();
    		} catch (InterruptedException e) {
			e.printStackTrace();
    		}
    		
    	}
    }
}

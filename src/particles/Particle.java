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
		//this.controller.getCanvas().getGraphicsContext2D().setFill(Color.WHITE);
		//this.controller.getCanvas().getGraphicsContext2D().fillOval(x, y, (Particle.SIZE*1.2), (Particle.SIZE*1.2));

	}

	/**
	 * met Ã  jour la position de la particule
	 */
	public void update() {
		this.x = this.x + this.vx;
		this.y = this.y + this.vy;
		for(Particle particle : this.controller.getParticle()){
			this.collisionTest(particle);
		}
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
		while(isVisible()){
		try {
			Thread.sleep(25);
			this.update();;
			this.controller.drawParticles();
			this.rebondir();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		//this.controller.removeParticle(this);
		//this.controller.addParticle();
	}

	public void rebondir(){
		if (this.x>=200-(Particle.SIZE) || this.x<=0+(Particle.SIZE)){
			this.vx=-vx;
		}
		if (this.y>=200-(Particle.SIZE) || this.y<=0+(Particle.SIZE)){
			this.vy=-vy;
		}
	}
	

    /**
     * effectue un test de collision entre la particule courante et
     * la particule p. S'il y a collision, les vitesses des particules sont
     * recalculées en fonction des vitesses initiales.
     * @param p particule à tester
     */
    private void collisionTest(Particle p) {
        if (p == this) {
            return;
        }
        double nx = x - p.x;
        double ny = y - p.y;
        double d = Math.sqrt(nx * nx + ny * ny);
        if (d <= SIZE && d > 0.0001) {
            nx = nx * (SIZE - d) / d;
            ny = ny * (SIZE - d) / d;
            x += nx * 0.5;
            y += ny * 0.5;
            p.x -= nx * 0.5;
            p.y -= ny * 0.5;
            d = Math.sqrt(nx * nx + ny * ny);
            nx /= d;
            ny /= d;
            double vn = (vx - p.vx) * nx + (vy - p.vy) * ny;
            if (vn > 0.0) {
                return;
            }
            vn *= -0.9;
            nx *= vn;
            ny *= vn;
            vx += nx;
            vy += ny;
            p.vx -= nx;
            p.vy -= ny;
        }
    }
    
	
}


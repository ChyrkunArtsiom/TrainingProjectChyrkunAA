package by.chyrkun.training.service.command;

/**
 * The abstract class which indicates that {@link Command} object executes another during it's execution.
 */
public abstract class BaseCommand implements Command {
    /**
     * The next {@link Command} that will be executed.
     */
    public BaseCommand next;

    /**
     * Sets next command to be executed.
     *
     * @param next the {@link Command} object which will be executed
     */
    public void setNext(BaseCommand next) {
        this.next = next;
    }
}

package by.chyrkun.training.service.command;

public abstract class BaseCommand implements Command {
    public BaseCommand next;

    public void setNext(BaseCommand next) {
        this.next = next;
    }
}

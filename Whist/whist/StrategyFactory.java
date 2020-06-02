public class StrategyFactory {
    public static StrategyFactory instance = new StrategyFactory();
    private String defaultStrat = "basic";
    
    public static StrategyFactory getInstance() {
        return instance;
    }

    public ICardStrategy getStrategy(String strategy) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (ICardStrategy) Class.forName(strategy).newInstance();
    }

}

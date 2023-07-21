public class TInstrument {
    private String name;
    private int index;
    private String upperBoundName;
    private String lowerBoundName;
    private int upperBoundNum;
    private int lowerBoundNum;
    private int range;

    TInstrument(String name, int index, String upperBoundName, String lowerBoundName) {
        this.name = name;
        this.index = index;
        this.upperBoundName = upperBoundName;
        this.lowerBoundName = lowerBoundName;
        this.upperBoundNum = NoteNameValueConverter.getNoteValue(upperBoundName);
        this.lowerBoundNum = NoteNameValueConverter.getNoteValue(lowerBoundName);
        this.range = upperBoundNum - lowerBoundNum;
    }

    String getName() { return name; }
    int getIndex() { return index; }
    String getUpperBoundName() { return upperBoundName; }
    String getLowerBoundName() { return lowerBoundName; }
    int getUpperBoundNum() { return upperBoundNum; }
    int getLowerBoundNum() { return lowerBoundNum; }
    int getRange() { return range; }

    @Override
    public String toString() {
        return name;
    }

}

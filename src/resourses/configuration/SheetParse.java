package resourses.configuration;

import java.util.ArrayList;

public class SheetParse extends Object {
    private final String name;
    private final String value;
    private final ArrayList<SheetParse> sheets;
    private final SheetParse parent;
    private final int depth;

    public SheetParse(String name, String value, ArrayList<SheetParse> sheets,
                      SheetParse parent, int depth) {
        this.name = name;
        this.value = value;
        this.sheets = sheets;
        this.parent = parent;
        this.depth = depth;
    }

    public SheetParse(SheetParse sheetParse) {
        this.name = sheetParse.getName();
        this.value = sheetParse.getValue();
        this.sheets = sheetParse.getSheets();
        this.parent = sheetParse.getParent();
        this.depth = sheetParse.getDepth();

    }

    public SheetParse getHead() {
        // if true - this object is head Sheet.
        if (parent == null) {
            return this;
        }

        return parent.getHead();
    }

    // find obj SheetParse with current name

    public SheetParse findSheetParseByName(String name) {
        SheetParse sheetParse = this.getHead();
        return sheetParse.findSheetParse(name, sheetParse);
    }

    private SheetParse findSheetParse(String name, SheetParse sheetParse) {
        if (sheetParse.getName().equals(name)) {
            return sheetParse;
        }

        ArrayList<SheetParse> localSheets = sheetParse.getSheets();
        if (localSheets == null) {
            return null;
        } else {
            for (SheetParse parse : localSheets) {
                if (parse != null) {
                    SheetParse output = parse.findSheetParse(name, parse);
                    if (output != null) {
                        return output;
                    }
                }
            }
        }
        return null;
    }

    // return SheetParse - this is future! it is not good, but i cant enoth
    public SheetParse addNewChildHeader(String name) {
        SheetParse sheetParse = new SheetParse(name, null,
                new ArrayList<SheetParse>(), this, depth + 1);
        sheets.add(sheetParse);
        return sheetParse;
    }

    public void addNewChildPair(String name, String value) {
        sheets.add(new SheetParse(name, value, null, this, depth + 1));
    }

    public String getName() {
        if (name != null) {
            return name.trim();
        }
        return null;
    }

    public String getValue() {
        if (value != null) {
            return value.trim();
        }
        return null;
    }

    public ArrayList<SheetParse> getSheets() {
        if (sheets == null) {
            return null;
        } else {
            return new ArrayList<SheetParse>(sheets);
        }

    }

    public SheetParse getParent() {
        if (parent == null) {
            return null;
        }
        return new SheetParse(parent);
    }

    public int getDepth() {
        return depth;
    }

    public boolean


    isComplex() {
        return ((sheets != null) && (!sheets.isEmpty()));
    }

    //rekyrs
    //translate object to text format, only current object, not all tree
    @Override
    public String toString() {
        String resultString = "";
        for (int i = 0; i < depth; i++) {
            resultString += "\t";
        }
        resultString += name;
        if (value != null) {
            resultString += " = " + value + "\n";
        } else {
            resultString += "\n";
        }
        if (sheets != null) {
            for (SheetParse list : sheets) {
                resultString += list.toString();
            }
        }
        return resultString;
    }
}
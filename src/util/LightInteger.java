package util;

public class LightInteger {
	public int data;
	public LightInteger(int data){
		this.data = data;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LightInteger that = (LightInteger) o;

        if (data != that.data) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data;
    }

    @Override
    public String toString(){
           return Integer.toString(data);
    }

}

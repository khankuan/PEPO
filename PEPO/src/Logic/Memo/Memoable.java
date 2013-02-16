package Logic.Memo;

import java.util.Date;

public interface Memoable extends Comparable<Memoable> {

    Date date = new Date();

    Date getDate();
}

package junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitTest {
    @Test
    public void throwsNPE()  {
        List<Integer> l = mock(ArrayList.class);
        doCallRealMethod().when(l).add(any(Integer.class));
        // java.lang.NullPointerException: Cannot read the array length because "elementData" is null
        l.add(100);
        l.size();
        verify(l, times(1)).add(0, 100);
        verify(l, times(1)).size();
        Integer result = l.get(0);
        assertEquals(100, result);
    }

    @Test
    public void doNotthrowsNPE()  {
        List<Integer> l = spy(ArrayList.class);
        doCallRealMethod().when(l).add(any(Integer.class));
        l.add(100);
        l.size();
        verify(l, times(1)).add(100);
        verify(l, times(1)).size();
        Integer result = l.get(0);
        assertEquals(100, result);
    }


}

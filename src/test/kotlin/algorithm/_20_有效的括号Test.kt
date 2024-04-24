import net.wu_chinese.leetcode.stack._20_有效的括号
import org.junit.Assert
import org.junit.Test

class _20_有效的括号Test {

    @Test
    fun test_20_有效的括号() {
        val solve = _20_有效的括号()
        Assert.assertFalse(solve.isValid2("(){}}{"))
        Assert.assertTrue(solve.isValid2("()"))
        Assert.assertTrue(solve.isValid2("[]"))
        Assert.assertTrue(solve.isValid2("{}"))
        Assert.assertTrue(solve.isValid2("(){}{}"))
        Assert.assertFalse(solve.isValid2("["))
    }
}

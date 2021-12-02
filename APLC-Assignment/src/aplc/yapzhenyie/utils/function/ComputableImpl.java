/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils.function;

/**
 *
 * @author Yap Zhen Yie
 */
public class ComputableImpl implements Computable<Integer, Integer> {

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }
    
}

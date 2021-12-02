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
@FunctionalInterface
public interface Computable<T, R> {

    public abstract R add(T i1, T i2);
    
}

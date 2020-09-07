/*
 * Created: 07.09.2020
 * Copyright (c) Saxess AG. All rights reserved.
 */

package de.befrish.testdatamt.api;

import de.befrish.testdatamt.tree.TreeNode;

/**
 * @author Benno Müller
 */
public interface TestDataProcessor {

    TreeNode processTestData(TreeNode rootNode);

}

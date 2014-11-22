/*
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2014, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package schemacrawler.tools.lint;


import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.NamedObjectWithAttributes;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.Config;

public abstract class BaseLinter
  implements Linter
{

  private static final Logger LOGGER = Logger.getLogger(BaseLinter.class
    .getName());

  private LintCollector collector;

  private LintSeverity severity = LintSeverity.medium;

  private Catalog catalog;

  @Override
  public void config(final LinterConfig linterConfig)
  {
    if (linterConfig != null)
    {
      setSeverity(linterConfig.getSeverity());
      config(linterConfig.getConfig());
    }
  }

  @Override
  public String getId()
  {
    return getClass().getName();
  }

  @Override
  public LintCollector getLintCollector()
  {
    return collector;
  }

  @Override
  public final LintSeverity getSeverity()
  {
    return severity;
  }

  @Override
  public final void lint(final Catalog catalog)
  {
    if (catalog == null)
    {
      throw new IllegalArgumentException("No database provided");
    }

    this.catalog = catalog;
    start();
    for (final Table table: catalog.getTables())
    {
      lint(table);
    }
    end();
    this.catalog = null;

  }

  @Override
  public void setLintCollector(final LintCollector lintCollector)
  {
    collector = lintCollector;
  }

  @Override
  public final void setSeverity(final LintSeverity severity)
  {
    if (severity != null)
    {
      this.severity = severity;
    }
  };

  protected <V extends Serializable> void addLint(final NamedObjectWithAttributes namedObject,
                                                  final String message,
                                                  final V value)
  {
    LOGGER.log(Level.FINE, String.format("Found lint for %s: %s --> %s",
                                         namedObject,
                                         message,
                                         value));
    if (collector != null)
    {
      final Lint<V> lint = newLint(namedObject.getFullName(), message, value);
      collector.addLint(namedObject, lint);
    }
  };

  protected <V extends Serializable> void addLint(final String message,
                                                  final V value)
  {
    if (catalog != null)
    {
      addLint(catalog, message, value);
    }
  }

  protected void config(final Config config)
  {

  }

  protected void end()
  {
  }

  protected abstract void lint(Table table);

  protected void start()
  {
  }

  private <V extends Serializable> Lint<V> newLint(final String objectName,
                                                   final String message,
                                                   final V value)
  {
    return new SimpleLint<>(getId(), objectName, getSeverity(), message, value);
  }

}

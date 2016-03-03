// Copyright 2016 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.domain.registry.keyring.api;

import dagger.Module;
import dagger.Provides;

import javax.annotation.concurrent.Immutable;

/** Dagger keyring module that always returns {@link VoidKeyring} instances. */
@Module
@Immutable
public final class VoidKeyringModule {

  /** Always returns a {@link VoidKeyring} instance. */
  @Provides
  static Keyring provideKeyring() {
    return new VoidKeyring();
  }
}
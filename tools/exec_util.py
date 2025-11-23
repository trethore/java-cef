# Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
# reserved. Use of this source code is governed by a BSD-style license that
# can be found in the LICENSE file

from subprocess import Popen, PIPE
import shlex
import sys
from typing import Optional, Union


def _prepare_command(cmd):
  """Return arguments suited for Popen on the current platform."""
  use_shell = sys.platform == 'win32'
  if use_shell:
    return cmd, True
  return shlex.split(cmd), False


def exec_cmd(cmd: str,
             path: str,
             input_string: Optional[Union[bytes, str]] = None):
  """Execute the specified command and return the result."""
  out = ''
  err = ''
  ret = -1
  popen_args, use_shell = _prepare_command(cmd)
  input_data = input_string
  if isinstance(input_string, bytes):
    input_data = input_string.decode('utf-8')
  try:
    process = Popen(
        popen_args,
        cwd=path,
        stdin=PIPE if input_string is not None else None,
        stdout=PIPE,
        stderr=PIPE,
        shell=use_shell,
        text=True,
        encoding='utf-8')
    out, err = process.communicate(input=input_data)
    ret = process.returncode
  except OSError:
    raise
  return {'out': out, 'err': err, 'ret': ret}

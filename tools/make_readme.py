# Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
# reserved. Use of this source code is governed by a BSD-style license that
# can be found in the LICENSE file.

import argparse
import sys
from pathlib import Path

from date_util import get_date
from file_util import path_exists, read_file, write_file
from readme_util import read_readme_file
import git_util as git

SCRIPT_DIR = Path(__file__).resolve().parent
JCEF_DIR = SCRIPT_DIR.parent
SUPPORTED_PLATFORMS = {
    'win32': 'Windows 32-bit',
    'win64': 'Windows 64-bit',
    'macosx64': 'Mac OS-X 64-bit',
    'linux32': 'Linux 32-bit',
    'linux64': 'Linux 64-bit',
}


def get_readme_component(name: str, platform: str) -> str:
  """Load a README component, preferring the platform directory."""
  candidate_dirs = [SCRIPT_DIR / 'distrib' / platform, SCRIPT_DIR / 'distrib']
  for path in candidate_dirs:
    file_path = path / f'README.{name}.txt'
    if path_exists(file_path):
      return read_file(file_path)
  raise FileNotFoundError(f'Readme component not found: {name}')


def build_readme_body(platform: str, metadata: dict) -> str:
  header_data = get_readme_component('header', platform)
  mode_data = get_readme_component('standard', platform)
  redistrib_data = get_readme_component('redistrib', platform)
  footer_data = get_readme_component('footer', platform)

  data = header_data + '\n\n' + mode_data + '\n\n' + redistrib_data + '\n\n' + footer_data
  for key, value in metadata.items():
    data = data.replace(key, value)

  try:
    platform_str = SUPPORTED_PLATFORMS[platform]
  except KeyError as exc:
    raise ValueError(f'Unsupported target "{platform}"') from exc

  return data.replace('$PLATFORM$', platform_str)


def create_readme(args, metadata):
  readme_contents = build_readme_body(args.platform, metadata)
  output_path = Path(args.output_dir) / 'README.txt'
  write_file(output_path, readme_contents)
  if not args.quiet:
    sys.stdout.write('Creating README.TXT file.\n')


def parse_args(argv):
  parser = argparse.ArgumentParser(
      description="Build the JCEF README.txt for the distribution.")
  parser.add_argument(
      '--output-dir',
      dest='output_dir',
      required=True,
      metavar='DIR',
      help='output directory')
  parser.add_argument(
      '--platform',
      required=True,
      choices=sorted(SUPPORTED_PLATFORMS.keys()),
      help='target platform for distribution')
  parser.add_argument(
      '-q',
      '--quiet',
      action='store_true',
      dest='quiet',
      default=False,
      help='do not output detailed status information')
  return parser.parse_args(argv)


def main(argv=None):
  args = parse_args(argv or sys.argv[1:])

  readme_values = {}
  read_readme_file(JCEF_DIR / 'jcef_build' / 'README.txt', readme_values)

  if not git.is_checkout(JCEF_DIR):
    raise Exception(f'Not a valid checkout: {JCEF_DIR}')

  jcef_commit_number = git.get_commit_number(JCEF_DIR)
  jcef_commit_hash = git.get_hash(JCEF_DIR)
  jcef_url = git.get_url(JCEF_DIR)
  jcef_ver = '%s.%s.%s.%s+g%s' % (readme_values['CEF_MAJOR'],
                                  readme_values['CEF_MINOR'],
                                  readme_values['CEF_PATCH'],
                                  jcef_commit_number, jcef_commit_hash[:7])

  metadata = {
      '$JCEF_URL$': jcef_url,
      '$JCEF_REV$': jcef_commit_hash,
      '$JCEF_VER$': jcef_ver,
      '$CEF_URL$': readme_values['CEF_URL'],
      '$CEF_VER$': readme_values['CEF_VER'],
      '$CHROMIUM_URL$': readme_values['CHROMIUM_URL'],
      '$CHROMIUM_VER$': readme_values['CHROMIUM_VER'],
      '$DATE$': get_date(),
  }

  create_readme(args, metadata)


if __name__ == "__main__":
  main()
